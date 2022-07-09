package de.votuucraft.gametables.api.inventory;

import de.votuucraft.gametables.GameTables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInventory implements Listener {

    private Inventory inventory;
    private Map<Integer, InventoryView> views;
    private Map<Integer, Integer> playerSlots;
    private String name;
    protected InventoryView active;
    private GameState state;

    public GameInventory(String name) {
        this.name = name;
        this.inventory = Bukkit.createInventory(null, 6*9, "§9" + name);
        this.views = new HashMap<>();
        this.playerSlots = new HashMap<>();
        this.state = GameState.WAITING;

        playerSlots.put(1, -1);
        playerSlots.put(7, -1);

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(GameTables.class));
    }

    public void update() {
        for(InventoryView view : views.values()) {
            view.getInventory().setContents(inventory.getContents());
        }
    }
    public void tell(String message) {
        views.values().forEach(view -> {
            if(Bukkit.getPlayer(view.getUniqueId()) != null) {
                Bukkit.getPlayer(view.getUniqueId()).sendMessage(GameTables.prefix(name) + message);
            }
        });
    }
    public void join(InventoryView view){
        int i = views.size() + 1;
        views.put(i, view);
        playerSlots.put(freePlayerSlot(), i);

        tell(i + "");

        tell("§9" + Bukkit.getOfflinePlayer(view.getUniqueId()).getName() + " §7hat die Warteschlange betreten. §8(§9" + views.size() + "§8/§7" + playerSlots.size() + "§8)");

        Player player = Bukkit.getPlayer(view.getUniqueId());
        player.openInventory(view.getInventory());
        setPlayer(1, view);

        if(views.size() == playerSlots.size()) {
            state = GameState.STARTED;
            active = views.get(1);

            for(int slots : playerSlots.keySet()) {
                setPlayer(slots, views.get(playerSlots.get(slots)));
            }
        }
    }

    private int freePlayerSlot() {
        for(int slot : playerSlots.keySet()) {
            if(playerSlots.get(slot) == -1) {
                return slot;
            }
        }
        return 0;
    }
    public void nextActive() {
        int current = byView(active);

        if(current == 1) {
            active = views.get(2);
        }else {
            active = views.get(1);
        }

        /*for(int i = current; i < 9; i++) {
            if(views.containsKey(i)) {
                active = views.get(i);
                return;
            }
        }

        for(int i = 1; i < 9; i++) {
            if(views.containsKey(i)) {
                active = views.get(i);
                return;
            }
        }*/

        for(int slots : playerSlots.keySet()) {
            setPlayer(slots, views.get(playerSlots.get(slots)));
        }
    }
    private int byView(InventoryView view) {
        for(int i = 0; i < 9; i++) {
            if(views.containsKey(i)) {
                if(views.get(i) == view) {
                    return i;
                }
            }
        }
        return 0;
    }

    public boolean equals(InventoryView view) {
        return views.containsValue(view);
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        update();
    }

    public void setPlayer(int slot, InventoryView view) {
        inventory.setItem(slot, player(view));
        inventory.setItem(slot + 9, playerStatus((active == view)));

        update();
    }

    protected ItemStack player(InventoryView view) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName((view == null ? "§c?" : "§9" + Bukkit.getPlayer(view.getUniqueId()).getName()));
        meta.setOwningPlayer((view == null ? null : Bukkit.getOfflinePlayer(view.getUniqueId())));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack playerStatus(boolean active) {
        if(this.active == null) {
            active = false;
        }

        ItemStack item = new ItemStack((active ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((active ? "§a§l✔" : "§c§l✘"));
        item.setItemMeta(meta);
        return item;
    }
    protected ItemStack air() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

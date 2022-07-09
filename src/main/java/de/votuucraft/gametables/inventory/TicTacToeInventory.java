package de.votuucraft.gametables.inventory;

import de.votuucraft.gametables.api.inventory.GameInventory;
import de.votuucraft.gametables.api.inventory.InventoryView;
import de.votuucraft.gametables.api.inventory.ViewController;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicTacToeInventory extends GameInventory {

    Map<Integer, InventoryView> slots;
    List<Integer> usableSlots;

    public TicTacToeInventory() {
        super("Tic-Tac-Toe");

        this.slots = new HashMap<>();
        this.usableSlots = new ArrayList<>();

        usableSlots.add(21);
        usableSlots.add(22);
        usableSlots.add(23);
        usableSlots.add(30);
        usableSlots.add(31);
        usableSlots.add(32);
        usableSlots.add(39);
        usableSlots.add(40);
        usableSlots.add(41);

        setPlayer(1, null);
        setPlayer(7, null);

        setItem(11, air());
        setItem(12, air());
        setItem(13, air());
        setItem(14, air());
        setItem(15, air());
        setItem(20, air());
        setItem(24, air());
        setItem(29, air());
        setItem(33, air());
        setItem(38, air());
        setItem(42, air());
        setItem(47, air());
        setItem(48, air());
        setItem(49, air());
        setItem(50, air());
        setItem(51, air());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() == getInventory()) {
            event.setCancelled(true);
            return;
        }

        if(event.getClickedInventory() == ViewController.getCache().get(player.getUniqueId()).getInventory()) {
            event.setCancelled(true);

            InventoryView view = ViewController.getCache().get(player.getUniqueId());

            if(active == view) {
                if (usableSlots.contains(event.getSlot())) {
                    slots.put(event.getSlot(), view);
                    setItem(event.getSlot(), player(view));
                    nextActive();
                }else if (slots.containsKey(event.getSlot())) {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                }
            }
        }
    }
}

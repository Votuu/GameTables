package de.votuucraft.gametables.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class InventoryView {

    private Inventory inventory;
    private UUID uuid;
    private GameInventory game;
    int playerSlot;

    public InventoryView(UUID uuid,
                         GameInventory game) {
        this.inventory = Bukkit.createInventory(null, 6*9, "§9Tic-Tac-Toe §7§oview");
        this.uuid = uuid;
        this.game = game;
        this.playerSlot = playerSlot;

        ViewController.getCache().put(uuid, this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public UUID getUniqueId() {
        return uuid;
    }
}

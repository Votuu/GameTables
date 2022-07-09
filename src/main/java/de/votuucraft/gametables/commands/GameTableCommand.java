package de.votuucraft.gametables.commands;

import de.votuucraft.gametables.GameTables;
import de.votuucraft.gametables.api.inventory.InventoryView;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class GameTableCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        InventoryView view = new InventoryView(player.getUniqueId(), GameTables.getTicTacToeInventory());

        GameTables.getTicTacToeInventory().join(view);
        return false;
    }
}

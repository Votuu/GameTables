package de.votuucraft.gametables;

import de.votuucraft.gametables.commands.GameTableCommand;
import de.votuucraft.gametables.inventory.TicTacToeInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class GameTables extends JavaPlugin {

    private static TicTacToeInventory ticTacToeInventory;

    @Override
    public void onEnable() {
        ticTacToeInventory = new TicTacToeInventory();

        getCommand("gametable").setExecutor(new GameTableCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TicTacToeInventory getTicTacToeInventory() {
        return ticTacToeInventory;
    }

    public static String prefix(String name) {
        return " §8\u25cf §9" + name + " §8| §7";
    }
    public static String prefix() {
        return " §8\u25cf §9GameTables §8| §7";
    }
}

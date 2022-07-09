package de.votuucraft.gametables.api.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewController {

    private static Map<UUID, InventoryView> cache = new HashMap<>();

    public static Map<UUID, InventoryView> getCache() {
        return cache;
    }
}

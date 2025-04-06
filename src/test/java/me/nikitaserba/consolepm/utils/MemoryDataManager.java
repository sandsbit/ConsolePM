package me.nikitaserba.consolepm.utils;

import java.util.HashMap;
import java.util.Map;

public class MemoryDataManager implements DataManager {

    private final Map<String, String> data;

    public MemoryDataManager() {
        data = new HashMap<>();
    }

    @Override
    public void save(String key, String value) {
        data.put(key, value);
    }

    @Override
    public String get(String key) {
        return data.getOrDefault(key, "");
    }

    @Override
    public void delete(String key) {
        data.remove(key);
    }
}

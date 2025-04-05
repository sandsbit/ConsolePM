package me.nikitaserba.consolepm.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDataManager implements DataManager {

    Path appSettingsFolder;

    public FileDataManager() {
        appSettingsFolder = OSUtils.getAppSettingsStorageDirectory();
    }

    @Override
    public void save(String key, String value) throws IOException {
        File file = getFileByKey(key);
        try (var writer = new FileWriter(file)) {
            writer.write(value);
        }
    }

    @Override
    public String get(String key) throws IOException {
        File file = getFileByKey(key);
        if (file.exists())
            return String.join("\n", Files.readAllLines(file.toPath()));
        else
            return "";
    }

    @Override
    public void delete(String key) {
        File file = getFileByKey(key);
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    private File getFileByKey(String key) {
        return appSettingsFolder.resolve(key).toFile();
    }
}

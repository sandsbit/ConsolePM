package me.nikitaserba.consolepm.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DataManager {

    void save(String key, String value) throws IOException;
    String get(String key) throws IOException;
    void delete(String key) throws IOException;

}

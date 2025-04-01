package me.nikitaserba.consolepm.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public final class OSUtils {

    private static final String APP_DIR_NAME = "consolepm";

    private OSUtils() {}

    /**
     * Returns OS-specific path where apps store their data:
     * - %AppData% for Windows
     * - User's Application Support for macOS
     * - home dir for linux
     */
    public static Path getSystemSettingsStorageDirectory() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        } else if (os.contains("win")) {
            return Paths.get(System.getenv("APPDATA"));
        }
        else
            return Paths.get(System.getProperty("user.home"));
    }

    public static Path getAppSettingsStorageDirectory() {
        return getSystemSettingsStorageDirectory().resolve(APP_DIR_NAME);
    }

}

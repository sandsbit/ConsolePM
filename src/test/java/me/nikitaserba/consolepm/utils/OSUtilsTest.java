package me.nikitaserba.consolepm.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OSUtilsTest {
    @Test
    void getSystemSettingsStorageDirectory() {
        Path systemStorageDirectory = OSUtils.getSystemSettingsStorageDirectory();

        assertNotNull(systemStorageDirectory);
        assertTrue(systemStorageDirectory.toFile().exists(), "System storage directory does not exist");
        assertTrue(systemStorageDirectory.toFile().isDirectory(), "System storage directory is not a directory");
    }

    @Test
    void getAppSettingsStorageDirectory() {
        Path systemStorageDirectory = OSUtils.getSystemSettingsStorageDirectory();
        Path appSettingStorageDirectory = OSUtils.getAppSettingsStorageDirectory();

        assertNotNull(appSettingStorageDirectory);
        assertFalse(appSettingStorageDirectory.toFile().isFile(), "App settings storage directory must not be a file");
        assertEquals(systemStorageDirectory, appSettingStorageDirectory.getParent(),
                "App settings storage directory must be in a system directory for app's data storing");
    }
}
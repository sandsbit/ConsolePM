package me.nikitaserba.consolepm.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileDataManagerTest {

    @Test
    void testReadWithNoInfo() throws IOException {
        DataManager dataManager = new FileDataManager();
        assertEquals("", dataManager.get("TESTKEY1"));
    }

    @Test
    void testWriteRead() throws IOException {
        DataManager dataManager = new FileDataManager();

        assertEquals("", dataManager.get("TESTKEY2"));

        dataManager.save("TESTKEY2", "TESTVALUE");

        assertEquals("TESTVALUE", dataManager.get("TESTKEY2"));

        dataManager.delete("TESTKEY2");

        assertEquals("", dataManager.get("TESTKEY2"));
    }

}
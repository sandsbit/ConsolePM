package me.nikitaserba.consolepm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void emptyEqualsAndHashCodeTest() {
        Account account = new Account();
        Account account2 = new Account();

        assertEquals(account, account2, "Two empty objects must be equal");
        assertEquals(account.hashCode(), account2.hashCode(), "Two empty objects must have the same hash code");
    }

    @Test
    void nonEmptyEqualsAndHashCodeTest() {
        Date date = new Date();
        Account account = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, date);
        Account account2 = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, (Date) date.clone());

        assertEquals(account, account2);
        assertEquals(account.hashCode(), account2.hashCode(), "Two equal objects must have the same hash code");
    }

    @Test
    void differentEqualsAndHashCodeTest() throws InterruptedException {
        Date date1 = new Date();
        Account account = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, date1);
        Account account2 = new Account("Test2", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, date1);
        Account account3 = new Account("Test", "pavlo", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, date1);
        Account account4 = new Account("Test", "nikita", "nikitaserba@gmail.com",
                new byte[] {0x16, 0x12}, date1);
        Account account5 = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x1F}, date1);
        TimeUnit.SECONDS.sleep(1);
        Account account6 = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, new Date());

        assertNotEquals(account, account2, "Accounts with different names aren't equal");
        assertNotEquals(account.hashCode(), account2.hashCode(), "Two different objects must have different hash codes");

        assertNotEquals(account, account3, "Accounts with different usernames aren't equal");
        assertNotEquals(account.hashCode(), account3.hashCode(), "Two different objects must have different hash codes");

        assertNotEquals(account, account4, "Accounts with different emails aren't equal");
        assertNotEquals(account.hashCode(), account4.hashCode(), "Two different objects must have different hash codes");

        assertNotEquals(account, account5, "Accounts with different passwords aren't equal");
        assertNotEquals(account.hashCode(), account5.hashCode(), "Two different objects must have different hash codes");

        assertNotEquals(account, account6, "Accounts with different created dates aren't equal");
        assertNotEquals(account.hashCode(), account6.hashCode(), "Two different objects must have different hash codes");
    }

    @Test
    void testSavingToJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Date date = new Date();
        Account account = new Account("Test", "nikita", "nikitaserba@icloud.com",
                new byte[] {0x16, 0x12}, date);

        String JSON = objectMapper.writeValueAsString(account);
        Account accountFromJSON = objectMapper.readValue(JSON, Account.class);

        assertEquals(account, accountFromJSON, "Account class was damaged while saving as JSON");
    }
}
package me.nikitaserba.consolepm;

import me.nikitaserba.consolepm.utils.Account;
import me.nikitaserba.consolepm.utils.MemoryDataManager;
import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    private PasswordManager passwordManager;

    @BeforeEach
    void setUp() throws Exception {
        MemoryDataManager dataManager = new MemoryDataManager();
        UserManager userManager = UserManager.getInstance(dataManager);
        userManager.newUser("test", "1111");
        passwordManager = PasswordManager.authenticate(dataManager, "test", "1111");
    }

    @Test
    void testAddAccount() throws EncryptionException, IOException {
        var account1 = new Account("account1", "user1", "email1@gmail.com", null, new Date());
        passwordManager.addAccount(account1, "1111");

        Account account = passwordManager.getAccountData("account1");
        assertEquals(account1.getName(), account.getName());
        assertEquals(account1.getEmail(), account.getEmail());
        assertEquals(account1.getUsername(), account.getUsername());
        assertEquals("1111", passwordManager.decryptPassword(account.getPasswordEncrypted()));
    }

    @Test
    void testAddTwoAccounts() throws EncryptionException, IOException {
        var account1 = new Account("account1", "user1", "email1@gmail.com", null, new Date());
        var account2 = new Account("account2", "user1", "email2@gmail.com", null, new Date());
        passwordManager.addAccount(account1, "1111");
        passwordManager.addAccount(account2, "2222");

        assertArrayEquals(new String[] {"account1", "account2"}, passwordManager.getAccountsNames().toArray(new String[0]));
    }

}
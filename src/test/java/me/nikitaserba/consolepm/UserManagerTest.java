package me.nikitaserba.consolepm;

import me.nikitaserba.consolepm.utils.MemoryDataManager;
import me.nikitaserba.consolepm.utils.exceptions.NoSuchUserException;
import me.nikitaserba.consolepm.utils.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    UserManager userManager;

    @BeforeEach
    void setUp() throws IOException {
        userManager = UserManager.getInstance(new MemoryDataManager());
    }

    @Test
    void createNewUser() throws IOException, NoSuchUserException, UserAlreadyExistsException {
        userManager.newUser("testuser", "testpassword");

        assertTrue(userManager.checkPassword("testuser", "testpassword"),
                "Password was correct but authentication failed");
        assertFalse(userManager.checkPassword("testuser", "test"),
                "True was given for false password");
        assertThrows(NoSuchUserException.class, () -> userManager.checkPassword("testuser2", ""));
        assertThrows(UserAlreadyExistsException.class, () -> userManager.newUser("testuser", "testpassword2"));
    }
}
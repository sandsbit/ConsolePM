package me.nikitaserba.consolepm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.nikitaserba.consolepm.utils.DataManager;
import me.nikitaserba.consolepm.utils.exceptions.NoSuchUserException;
import me.nikitaserba.consolepm.utils.exceptions.UserAlreadyExistsException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Singleton class for working with users.
 */
public class UserManager {

    private final DataManager dataManager;
    private final ObjectMapper objectMapper;
    private final Map<String, String> users;

    private final String DATA_KEY = "users";

    private static final HashMap<DataManager, UserManager> instances = new HashMap<>();
    public static UserManager getInstance(DataManager dataManager) throws IOException {
        if (instances.containsKey(dataManager)) {
            return instances.get(dataManager);
        } else {
           var userManager = new UserManager(dataManager);
           instances.put(dataManager, userManager);
           return userManager;
        }
    }

    private UserManager(DataManager dataManager) throws IOException {
        this.dataManager = dataManager;
        objectMapper = new ObjectMapper();
        String usersData = dataManager.get(DATA_KEY);
        if (usersData.isEmpty()) {
            users = new HashMap<>();
        } else {
            users = objectMapper.readValue(usersData, new TypeReference<>() {});
        }
    }

    public boolean checkPassword(String username, String password) throws NoSuchUserException {
        try {
            if (!users.containsKey(username))
                throw new NoSuchUserException();
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5 = new String(md.digest(password.getBytes()));
            return users.get(username).equals(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public void newUser(String username, String password) throws IOException, UserAlreadyExistsException {
        try {
            if (users.containsKey(username))
                throw new UserAlreadyExistsException();
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5 = new String(md.digest(password.getBytes()));
            users.put(username, md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        dataManager.save(DATA_KEY, objectMapper.writeValueAsString(users));
    }

    public void deleteUser(String username) throws NoSuchUserException, IOException {
        if (!users.containsKey(username))
            throw new NoSuchUserException();
        users.remove(username);
        dataManager.save(DATA_KEY, objectMapper.writeValueAsString(users));
    }

    public void changePassword(String username, String old_password, String new_password) {
        throw new UnsupportedOperationException("Changing user's password is not yet supported.");
    }
}

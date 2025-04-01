package me.nikitaserba.consolepm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.nikitaserba.consolepm.utils.exceptions.NoSuchUserException;
import me.nikitaserba.consolepm.utils.OSUtils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Singleton class for working with users.
 */
public class UserManager {

    private static final UserManager instance = new UserManager();
    public static UserManager getInstance() {
        return instance;
    }


    private static final String USERS_FILENAME = "users.json";
    private final File usersFile;
    private final ObjectMapper objectMapper;
    private Map<String, String> users;

    private UserManager() {
        objectMapper = new ObjectMapper();
        usersFile = OSUtils.getAppSettingsStorageDirectory().resolve(USERS_FILENAME).toFile();
        if (!usersFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            OSUtils.getAppSettingsStorageDirectory().toFile().mkdirs();
            users = new HashMap<>();
        } else {
            try {
                users = objectMapper.readValue(usersFile, new TypeReference<>() {});
            } catch (IOException e) {
                System.out.println("Error while reading user data: " + e.getMessage());
                System.exit(1);
            }
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

    public void newUser(String username, String password) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5 = new String(md.digest(password.getBytes()));
            users.put(username, md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        objectMapper.writeValue(usersFile, users);
    }

    public void deleteUser(String username) throws NoSuchUserException, IOException {
        if (!users.containsKey(username))
            throw new NoSuchUserException();
        users.remove(username);
        objectMapper.writeValue(usersFile, users);
    }

    public void changePassword(String username, String old_password, String new_password) {
        throw new UnsupportedOperationException("Changing user's password is not yet supported.");
    }
}

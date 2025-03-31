package me.nikitaserba.consolepm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.nikitaserba.consolepm.utils.*;
import me.nikitaserba.consolepm.utils.encryptors.AESGeneralPasswordEncryptor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordManager {

    private static final UserManager userManager = UserManager.getInstance();

    private final String username;
    private final String password;  // TODO: Remove final when adding password change

    File userPasswordsFile;
    private ObjectMapper objectMapper;
    private PasswordEncryptor passwordEncryptor;
    private List<Account> accounts;

    private PasswordManager(String username, String password) throws IOException, EncryptionException {
        this.username = username;
        this.password = password;

        this.userPasswordsFile = OSUtils.getAppSettingsStorageDirectory().resolve(username + ".json").toFile();

        this.objectMapper = new ObjectMapper();
        loadAccounts();

        this.passwordEncryptor = new AESGeneralPasswordEncryptor(password);
    }

    public static PasswordManager authenticate(String username, String password) throws NoSuchUserException,
                                                                                    IOException, EncryptionException {
        if (userManager.checkPassword(username, password))
            return new PasswordManager(username, password);
        else
            return null;
    }

    public List<String> getAccountsNames() {
        return accounts.stream().map(Account::getUsername).collect(Collectors.toList());
    }

    public Account getAccountData(String name) {
        for (Account account : accounts) {
            if (account.getName().equals(name))
                return account;
        }
        return null;
    }

    public String decryptPassword(byte[] password) throws EncryptionException {
        return passwordEncryptor.decrypt(password);
    }

    public void addAccount(Account account, String password) throws EncryptionException, IOException {
        account.setPasswordEncrypted(passwordEncryptor.encrypt(password));
        accounts.add(account);
        saveAccounts();
    }

    public void deleteAccount(String name) {
        accounts.removeIf(account -> account.getName().equals(name));
    }

    public void changeAccount(String name, Account newAccount, String password) throws EncryptionException, IOException {
        deleteAccount(name);
        addAccount(newAccount, password);
    }

    private void loadAccounts() throws IOException {
        if (!userPasswordsFile.exists())
            this.accounts = new ArrayList<>();
        else
            this.accounts = objectMapper.readValue(userPasswordsFile, new TypeReference<>() {});
    }

    private void saveAccounts() throws IOException {
        userPasswordsFile.getParentFile().mkdirs();
        objectMapper.writeValue(userPasswordsFile, accounts);
    }
}

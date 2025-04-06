package me.nikitaserba.consolepm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.nikitaserba.consolepm.utils.*;
import me.nikitaserba.consolepm.utils.encryptors.AESGeneralPasswordEncryptor;
import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;
import me.nikitaserba.consolepm.utils.exceptions.InvalidPasswordException;
import me.nikitaserba.consolepm.utils.exceptions.NoSuchUserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordManager {

    private final DataManager dataManager;

    private final String username;
    private final String password;  // TODO: Remove final when adding password change

    private final ObjectMapper objectMapper;  // JSON mapper
    private final PasswordEncryptor passwordEncryptor;
    private List<Account> accounts;

    private PasswordManager(DataManager dataManager, String username, String password) throws IOException,
            EncryptionException {
        this.dataManager = dataManager;
        this.username = username;
        this.password = password;

        this.objectMapper = new ObjectMapper();
        loadAccounts();

        this.passwordEncryptor = new AESGeneralPasswordEncryptor(password);
    }

    /**
     * Returns new PasswordManager instance, but only if password is correct.
     *
     * Password is checked by its md5 sum, this doesn't guarantee that key works for decryption.
     */
    public static PasswordManager authenticate(DataManager dataManager, String username,
                                               String password) throws NoSuchUserException, IOException,
            EncryptionException, InvalidPasswordException {
        var userManager = UserManager.getInstance(dataManager);
        if (userManager.checkPassword(username, password))
            return new PasswordManager(dataManager, username, password);
        else
            throw new InvalidPasswordException();
    }

    public List<String> getAccountsNames() {
        return accounts.stream().map(Account::getName).collect(Collectors.toList());
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
        String accountsData = dataManager.get(username);
        if (accountsData.isEmpty())
            this.accounts = new ArrayList<>();
        else
            this.accounts = objectMapper.readValue(accountsData, new TypeReference<>() {});
    }

    private void saveAccounts() throws IOException {
        dataManager.save(username, objectMapper.writeValueAsString(accounts));
    }
}

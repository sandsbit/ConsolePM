package me.nikitaserba.consolepm.utils;

import me.nikitaserba.consolepm.utils.exceptions.EncryptionException;

public interface PasswordEncryptor {

    byte[] encrypt(String password) throws EncryptionException;
    String decrypt(byte[] encryptedPassword) throws EncryptionException;

}

package me.nikitaserba.consolepm.utils;

public interface PasswordEncryptor {

    byte[] encrypt(String password) throws EncryptionException;
    String decrypt(byte[] encryptedPassword) throws EncryptionException;

}

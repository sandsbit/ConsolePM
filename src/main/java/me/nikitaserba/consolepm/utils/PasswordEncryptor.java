package me.nikitaserba.consolepm.utils;

public interface PasswordEncryptor {

    Byte[] encrypt(String password) throws EncryptionException;
    String decrypt(Byte[] encryptedPassword) throws EncryptionException;

}

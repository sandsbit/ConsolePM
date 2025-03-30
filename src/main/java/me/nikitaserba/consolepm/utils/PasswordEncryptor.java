package me.nikitaserba.consolepm.utils;

public interface PasswordEncryptor {

    Byte[] encrypt(String password);
    String decrypt(Byte[] encryptedPassword);

}

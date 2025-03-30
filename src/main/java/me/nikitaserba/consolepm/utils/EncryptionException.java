package me.nikitaserba.consolepm.utils;

public class EncryptionException extends RuntimeException {
    public EncryptionException(Exception e) {
        super(e);
    }
}
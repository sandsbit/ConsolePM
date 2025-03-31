package me.nikitaserba.consolepm.utils;

import java.util.Date;

public class Account {

    private final String name;
    private final String username;
    private final String email;
    private byte[] passwordEncrypted;
    private final Date created;

    public Account(String name, String username, String email, byte[] passwordEncrypted, Date created) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordEncrypted = passwordEncrypted;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public Date getCreated() {
        return created;
    }

    public void setPasswordEncrypted(byte[] passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }
}

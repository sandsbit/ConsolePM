package me.nikitaserba.consolepm.utils;

import java.util.Date;

public class Account {

    private String name;
    private String username;
    private String email;
    private byte[] passwordEncrypted;
    private Date created;

    public Account(String name, String username, String email, byte[] passwordEncrypted, Date created) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordEncrypted = passwordEncrypted;
        this.created = created;
    }

    // Used in Jackson
    public Account() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

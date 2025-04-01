package me.nikitaserba.consolepm.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

public class Account {

    private String name;
    private String username;
    private String email;
    private String passwordEncrypted;
    private Date created;

    public Account(String name, String username, String email, byte[] passwordEncrypted, Date created) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordEncrypted = passwordEncrypted == null ? null : new String(passwordEncrypted, StandardCharsets.UTF_8);
        this.created = created;
    }

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
        return passwordEncrypted.getBytes(StandardCharsets.UTF_8);
    }

    public Date getCreated() {
        return created;
    }

    public void setPasswordEncrypted(byte[] passwordEncrypted) {
        this.passwordEncrypted = new String(passwordEncrypted, StandardCharsets.UTF_8);
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

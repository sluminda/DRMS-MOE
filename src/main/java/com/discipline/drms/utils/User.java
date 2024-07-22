package com.discipline.drms.utils;

public class User {
    private final String username;
    private final String passwordHash;
    private final String salt;
    private final String role;

    public User(String username, String passwordHash, String salt, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
    }

    public boolean isValidRole() {
        return role.equals("Owner") || role.equals("Super Admin") || role.equals("Admin");
    }
}


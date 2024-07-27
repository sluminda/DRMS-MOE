package com.discipline.drms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenerateSampleData {

    private static final Logger logger = LoggerFactory.getLogger(GenerateSampleData.class);

    public static void main(String[] args) {
        try {
            createSampleUsers();
        } catch (SQLException e) {
            logger.error("Error creating sample users", e);
        }
    }

    public static void createSampleUsers() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/drms";
        String user = "root";
        String dbPassword = "1220";

        try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
            insertUser(conn, "lumi", "lumi", "Owner");
            insertUser(conn, "sanda", "sanda", "Super Admin");
            insertUser(conn, "Rashmi", "rashmi", "Admin");

        } catch (SQLException e) {
            logger.error("Database connection error", e);
            throw e;
        }
    }

    private static void insertUser(Connection conn, String username, String password, String role) throws SQLException {
        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);

        String sql = "INSERT INTO users (username, password_hash, salt, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.setString(4, role);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error inserting user: {}", username, e);
            throw e;
        }
    }
}
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private static final String DB_URL = System.getenv("DB_URL") == null ? "jdbc:mysql://localhost/db" : System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER") == null ? "root" : System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public void findUser(String username) {
        // ✅ Fix 1: no SELECT *
        String sql = "SELECT id, name FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LOGGER.log(Level.INFO, () -> "Found user: id=" + id + ", name=" + name);
                } else {
                    LOGGER.log(Level.INFO, () -> "User not found: " + username);
                }
            }
        } catch (SQLException e) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                String msg = "DB error while finding user: " + username;
                LOGGER.log(Level.SEVERE, msg, e);
            }
            throw new IllegalStateException("Failed to find user: " + username, e);
        }
    }

    public int deleteUser(String username) {
        // (If your lab had SQL injection issue here, PreparedStatement fixes it too.)
        String sql = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            int rows = ps.executeUpdate();
            LOGGER.log(Level.INFO, () -> "Deleted rows=" + rows + " for user=" + username);
            return rows;
        } catch (SQLException e) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                String msg = "DB error while deleting user: " + username;
                LOGGER.log(Level.SEVERE, msg, e);
            }
            throw new IllegalStateException("Failed to delete user: " + username, e);
        }
    }
}

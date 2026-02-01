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

    // keep these as your lab used (edit DB name if needed)
    private static final String DB_URL = "jdbc:mysql://localhost/db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public void findUser(String username) {
        // ✅ Fix 1: no SELECT *
        String sql = "SELECT id, name FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // ✅ Fix 2: use logger instead of System.out
                    LOGGER.info("Found user: id=" + rs.getInt("id") + ", name=" + rs.getString("name"));
                } else {
                    LOGGER.info("User not found: " + username);
                }
            }

        } catch (SQLException e) {
            // ✅ Fix 3: no generic exception; log + wrap
            LOGGER.log(Level.SEVERE, "DB error while finding user: " + username, e);
            throw new RuntimeException("Failed to find user: " + username, e);
        }
    }

    public int deleteUser(String username) {
        // (If your lab had SQL injection issue here, PreparedStatement fixes it too.)
        String sql = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            int rows = ps.executeUpdate();
            // ✅ Fix 2 again: logger
            LOGGER.info("Deleted rows=" + rows + " for user=" + username);
            return rows;

        } catch (SQLException e) {
            // ✅ Fix 3 again
            LOGGER.log(Level.SEVERE, "DB error while deleting user: " + username, e);
            throw new RuntimeException("Failed to delete user: " + username, e);
        }
    }
}

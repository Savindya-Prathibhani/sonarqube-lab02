package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
 // NOTE: In real apps, never hardcode these. Keep simple for the lab.
    private static final String DB_URL = "jdbc:mysql://localhost/db";
    private static final String DB_USER = "root";
    private final String password;

    public UserService() {
        // If your old code already had "password" variable, keep that approach.
        // Best: read from env var; fallback empty to avoid null.
        this.password = System.getenv().getOrDefault("DB_PASSWORD", "");
    }

    public void findUser(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Keep minimal output; adjust to your table columns if needed
                    System.out.println("Found user: " + rs.getString("name"));
                }
            }
        }
    }

    public void deleteUser(String username) throws Exception {
        String sql = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            int rows = ps.executeUpdate();
            System.out.println("Deleted rows: " + rows);
        }
    }
}

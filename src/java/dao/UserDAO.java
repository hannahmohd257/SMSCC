/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import model.User;

public class UserDAO {
    private Connection conn;

    // Constructor to initialize connection
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Method to generate new unique userID based on role
    public String generateNewUserID(String role) throws SQLException {
        String prefix;
        switch (role.toLowerCase()) {
            case "Staff": prefix = "S"; break;
            case "Finance Officer": prefix = "F"; break;
            case "Human Resource": prefix = "H"; break;
            case "Manager": prefix = "M"; break;
            default: throw new IllegalArgumentException("Invalid role: " + role);
        }

        String sql = "SELECT MAX(userID) FROM user WHERE role = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, role);
        ResultSet rs = stmt.executeQuery();

        int nextIdNum = 1;
        if (rs.next() && rs.getString(1) != null) {
            String maxId = rs.getString(1); // e.g., "S003"
            nextIdNum = Integer.parseInt(maxId.substring(1)) + 1;
        }

        return String.format("%s%03d", prefix, nextIdNum); // e.g., S004
    }

    // Method to insert a new user into user table
    public boolean insertUser(String role, String username, String plainPassword) throws SQLException {
        // Step 1: Generate userID based on role
        String userId = generateNewUserID(role);

        // Step 2: Hash the password (SHA-256)
        String hashedPassword = hashPassword(plainPassword);

        // Step 3: Insert into database
        String sql = "INSERT INTO user (userID, role, username, password) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userId);
        stmt.setString(2, role);
        stmt.setString(3, username);
        stmt.setString(4, hashedPassword);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Optional: Fetch user by username
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getString("userID"),
                rs.getString("role"),
                rs.getString("username"),
                rs.getString("password")
            );
        }
        return null;
    }
}


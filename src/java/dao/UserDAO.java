/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import util.DBConnection;

public class UserDAO {
    
//=== Password Hashing (Using BCrypt) ===
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    //=== Validate User (Combines check + fetch in one query) ===
    public User validateAndGetUser(String userID, String plainPassword, String role) {
        String sql = "SELECT * FROM user WHERE userID = ? AND role = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userID);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (checkPassword(plainPassword, storedHashedPassword)) {
                    return new User(
                        rs.getString("userID"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        storedHashedPassword,  // Already hashed
                        rs.getString("email")
                    );
                } else {
                System.out.println("Password mismatch!");
                }
            } 
        } catch (SQLException e) {
            System.err.println("SQL Error in validateAndGetUser: " + e.getMessage());
        }
        return null;  // Invalid credentials
    }
    // Generate new unique userID based on role
    // Modified generateNewUserID to accept a Connection
    public String generateNewUserID(String role, Connection conn) throws SQLException {
        String prefix;
        switch (role.toLowerCase()) {
            case "staff":
                prefix = "S";
                break;
            case "manager":
                prefix = "M";
                break;
            case "human resource":
                prefix = "H";
                break;
            case "finance officer":
                prefix = "F";
                break;
            default:
                prefix = "U";
                break;
        }

        String sql = "SELECT userID FROM user WHERE userID LIKE ? ORDER BY userID DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                int nextNumber = 1;
                if (rs.next()) {
                    String lastID = rs.getString("userID");
                    String numberPart = lastID.substring(2); // Remove prefix
                    nextNumber = Integer.parseInt(numberPart) + 1;
                }
                return String.format("%s%03d", prefix, nextNumber);
            }
        }
    }

    // Insert a new user into the user table
    public String insertUser(String role, String fullname, String username, String plainPassword, String email, Connection conn) throws SQLException {
        String userId = generateNewUserID(role, conn);
//        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        String sql = "INSERT INTO user (userID, fullname, username, password, email, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, fullname);
            stmt.setString(3, username);
            stmt.setString(4, plainPassword);
            stmt.setString(5, email);
            stmt.setString(6, role);

            int rows = stmt.executeUpdate();
            return rows > 0 ? userId : null;
        }
    }
    
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET fullname=?, username=?, email=?, role=? WHERE userID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getUserID());
            stmt.executeUpdate();
        }
    }
//    public void updateUser(User user) throws SQLException {
//        String sql = "UPDATE users SET username = ?, fullname = ?, email = ? WHERE userID = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getFullname());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getUserID());
//
//            stmt.executeUpdate();
//        }
//    }
    
    public void deleteUser(String userID) throws SQLException {
        String sql = "DELETE FROM user WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.executeUpdate();
        }
    }


    
    public User getUserById(String userID) {
        String sql = "SELECT * FROM user WHERE userID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("userID"),
                    rs.getString("role"),
                    rs.getString("username"),
                    rs.getString("fullname"),
                    rs.getString("password"),  // still hashed
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getUserById: " + e.getMessage());
        }

        return null;
    }

    public List<User> getUserByRole(String role) throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT userID, username, fullname, email, role FROM user WHERE role = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, role);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getString("userID"));
                    user.setUsername(rs.getString("username"));
                    user.setFullname(rs.getString("fullname"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));

//                    // Optional: If you want to also set the staff-specific info, do so here
//                    Staff staff = new Staff();
//                    staff.setStaffPosition(rs.getString("staffPosition"));
//                    user.setStaff(staff); // Assuming you added a 'Staff' field to 'User'

                    userList.add(user);
                }
            }
        }

        return userList;
    }
    
    public double getOvertimeHours(String userID, int year, int month) {
        double hours = 0;
        String sql = "SELECT SUM(hours) FROM overtime WHERE userID = ? AND YEAR(date) = ? AND MONTH(date) = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hours = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hours;
    }
    
    public double getDeductions(String userID, int year, int month) {
        double deductions = 0;
        String sql = "SELECT SUM(amount) FROM deductions WHERE userID = ? AND YEAR(date) = ? AND MONTH(date) = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setInt(2, year);
            ps.setInt(3, month);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                deductions = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deductions;
    }
    
    /**
     * Checks if a username already exists in the database
     * @param username The username to check
     * @return true if the username exists, false otherwise
     * @throws SQLException if there's a database error
     */
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
    
    // Alternative case-insensitive version if needed
    public boolean usernameExistsCaseInsensitive(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        String query = "SELECT COUNT(*) FROM user WHERE LOWER(username) = LOWER(?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, username.trim());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
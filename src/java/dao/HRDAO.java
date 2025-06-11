/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author user
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.DBConnection;

public class HRDAO {
    public boolean saveHR(String userID, String position, Connection conn) throws SQLException {
        String sql = "INSERT INTO manager (userID, position) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, position);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public void deleteHR(String userID) throws SQLException {
        String sql = "DELETE FROM manager WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.executeUpdate();
        }
    }

}

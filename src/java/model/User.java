/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class User {
    private String userID;
    private String role;
    private String username;
    private String fullname;
    private String password;
    private String email;

    // Default constructor
    public User() {
    }

    // Constructor with fields
    public User(String userID, String role, String username, String fullname, String password, String email) {
        this.userID = userID;
        this.role = role;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public double getBasicSalary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


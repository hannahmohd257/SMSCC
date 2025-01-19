/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public enum Role {
    GENERAL_STAFF(1),
    FINANCE_OFFICER(2),
    MANAGER(3);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // This method converts from integer to Role
    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }

    // This method converts from string to Role
    public static Role fromString(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role name: " + roleString);
        }
    }

    // This method allows you to get the name of the role as a string
    @Override
    public String toString() {
        return name();
    
//    public static Role fromInt(int i) {
//        switch (i) {
//            case 1: return GENERAL_STAFF;
//            case 2: return FINANCE_OFFICER;
//            case 3: return MANAGER;
//            default: return null;
//        }
    }
}



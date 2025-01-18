/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public enum Role {
    GENERAL_STAFF(1),
    FINANCE_OFFICER(2),
    MANAGER(3);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role staffRole : Role.values()) {
            if (staffRole.value == value) {
                return staffRole;
            }
        }
        return GENERAL_STAFF; // Default role
    }
}


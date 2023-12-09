package com.api.enums;

/**
 *
 * @author Miguel Castro
 */
public enum RoleEnum {
    
    ADMIN("Administrator"), 
    USER("User");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

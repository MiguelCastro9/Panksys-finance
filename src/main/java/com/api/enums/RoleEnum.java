package com.api.enums;

/**
 *
 * @author Miguel Castro
 */
public enum RoleEnum {
    
    ADMIN("Administrador"), 
    USER("Usuário");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

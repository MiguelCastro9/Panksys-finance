package com.api.enums;

/**
 *
 * @author Miguel Castro
 */
public enum StatusPaymentEnum {
    
    ALL_PAID("All paid"), 
    ALL_PENDING("All pending"), 
    ALL_LATE("All late");

    private String name;

    StatusPaymentEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

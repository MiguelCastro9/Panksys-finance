package com.api.enums;

/**
 *
 * @author Miguel Castro
 */
public enum FormPaymentEnum {

    DEBIT("Debit"),
    CREDIT("Credit"),
    MONEY("Money");

    private String name;

    FormPaymentEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

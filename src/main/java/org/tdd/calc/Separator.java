package org.tdd.calc;

public class Separator {
    private String value;
    private boolean custom;

    public Separator(String value, boolean custom) {
        this.value = value;
        this.custom = custom;
    }

    public String get() {
        return value;
    }

    public boolean isCustom() {
        return custom;
    }
}

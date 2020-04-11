package org.tdd.calc;

public class Separator {
    private String value;
    private boolean custom;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setCustom() {
        this.custom = true;
    }

    public boolean isCustom() {
        return custom;
    }
}

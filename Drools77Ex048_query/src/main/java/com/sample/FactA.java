package com.sample;

public class FactA {

    private String value;

    public FactA() {
    }

    public FactA(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FactA [value=" + value + "]";
    }

}

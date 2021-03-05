package com.sample;

public class FactB {

    private String value;

    public FactB() {
    }

    public FactB(String value) {
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
        return "FactB [value=" + value + "]";
    }

}

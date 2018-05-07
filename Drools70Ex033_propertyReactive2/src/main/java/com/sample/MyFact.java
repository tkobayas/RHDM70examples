package com.sample;

import org.kie.api.definition.type.PropertyReactive;

//@PropertyReactive
public class MyFact {

    private int a;
    private int b;

    public MyFact() {
    }

    public MyFact(int a, int b) {
        super();
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

}

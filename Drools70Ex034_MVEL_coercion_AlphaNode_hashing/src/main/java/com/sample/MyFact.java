package com.sample;

public class MyFact {

    private String strData;
    private Integer intData;

    public MyFact(String strData, Integer intData) {
        super();
        this.strData = strData;
        this.intData = intData;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }

    public Integer getIntData() {
        return intData;
    }

    public void setIntData(Integer intData) {
        this.intData = intData;
    }

    @Override
    public String toString() {
        return "MyFact [strData=" + strData + ", intData=" + intData + "]";
    }
}

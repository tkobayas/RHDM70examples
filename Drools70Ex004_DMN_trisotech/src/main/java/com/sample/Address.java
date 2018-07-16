package com.sample;


public class Address {

    private String zipcpde;
    
    private String street;

    
    
    public Address(String zipcpde, String street) {
        super();
        this.zipcpde = zipcpde;
        this.street = street;
    }


    public String getZipcpde() {
        return zipcpde;
    }

    
    public void setZipcpde(String zipcpde) {
        this.zipcpde = zipcpde;
    }

    
    public String getStreet() {
        return street;
    }

    
    public void setStreet(String street) {
        this.street = street;
    }
    
    
}

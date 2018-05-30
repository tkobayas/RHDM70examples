package com.sample;

import org.kie.api.definition.type.PropertyReactive;

//@PropertyReactive
public class Person {

    private String firstName;
    private String lastName;
    
    private boolean male;

    
    
    public Person(String firstName, String lastName, boolean male) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.male = male;
    }


    public String getFirstName() {
        return firstName;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    public boolean isMale() {
        return male;
    }

    
    public void setMale(boolean male) {
        this.male = male;
    }
    
    
}

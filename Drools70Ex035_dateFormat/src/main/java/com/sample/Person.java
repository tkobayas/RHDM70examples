package com.sample;

import java.util.Date;

public class Person {

    private String name;

    private int age;
    
    private Date birthDay;

    public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Person() {
    }

    public Person(String name, int age, Date birthDay) {
        this.name = name;
        this.age = age;
        this.birthDay = birthDay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package com.sample;

import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnit;

public class MyRuleUnit implements RuleUnit {

    private DataSource<Person> persons;

    public MyRuleUnit() {

    }

    public MyRuleUnit(DataSource<Person> persons) {

        this.persons = persons;
    }

    public DataSource<Person> getPersons() {
        return persons;
    }

    public void setPersons(DataSource<Person> persons) {
        this.persons = persons;
    }

}

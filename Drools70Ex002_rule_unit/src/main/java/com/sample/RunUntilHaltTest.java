package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnitExecutor;

/**
 * This is a sample class to launch a rule.
 */
public class RunUntilHaltTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieBase kieBase = kContainer.getKieBase();

            DataSource<Person> dataStream = DataSource.create();
            RuleUnitExecutor executor = RuleUnitExecutor.create().bind(kieBase);
            executor.bindVariable("persons", dataStream);

            System.out.println("Starting RuleUnit executor");
            new Thread(() -> executor.runUntilHalt(MyRuleUnit.class)).start();

            //Insert data using intervals.
            Thread.sleep(2000);
            System.out.println("Inserting data.");
            dataStream.insert(new Person("John", 34));
            Thread.sleep(2000);
            System.out.println("Inserting data.");
            dataStream.insert(new Person("Paul", 32));
            Thread.sleep(2000);
            System.out.println("Inserting data.");
            dataStream.insert(new Person("George", 31));

            System.out.println("Stopping RuleUnit executor.");

            executor.halt();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

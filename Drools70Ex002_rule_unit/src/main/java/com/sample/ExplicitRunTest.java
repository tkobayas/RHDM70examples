package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnitExecutor;

/**
 * This is a sample class to launch a rule.
 */
public class ExplicitRunTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieBase kieBase = kContainer.getKieBase();

            DataSource<Person> dataStream = DataSource.create(new Person("John", 34), new Person("Paul", 32), new Person("George", 31));
            RuleUnitExecutor executor = RuleUnitExecutor.create().bind(kieBase);

            MyRuleUnit myRuleUnit = new MyRuleUnit(dataStream);

            System.out.println("explicit run");
            executor.run(myRuleUnit);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

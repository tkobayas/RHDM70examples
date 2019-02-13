package com.sample;

import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest2 {

    public static final void main(String[] args) {
        try {
            
            // If you set sequential mode to true, you should not use re-evaluation by update/modify.
            // If you have update/modify in rules, the result may differ depending on rules ordering in a DRL file
            System.setProperty("drools.sequential", "true");
            
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession(); // can use only stateless

            // go !
            Person john = new Person("john", 20);

            kSession.insert(john);
            kSession.fireAllRules();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

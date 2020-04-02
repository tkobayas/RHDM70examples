package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();

            // go !
            Person p = new Person("Mario", 20);
            kSession.insert(p);
            kSession.fireAllRules();
            
            System.out.println(p.getAge());

            kSession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

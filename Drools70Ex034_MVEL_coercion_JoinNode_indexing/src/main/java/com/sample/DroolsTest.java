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
            MyFact fact1 = new MyFact("aaa", 20);
            MyFact fact2 = new MyFact("20", 100);

            kSession.insert(fact1);
            kSession.insert(fact2);
            
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

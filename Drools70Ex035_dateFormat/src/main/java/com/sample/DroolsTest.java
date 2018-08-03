package com.sample;

import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            System.setProperty("drools.defaultlanguage", "ja");
            System.setProperty("drools.defaultcountry", "JP");
            System.setProperty("drools.dateformat", "yyyy-MMM-dd"); // 2018-1月-12

//          System.setProperty("drools.dateformat", "yyyy-MM-dd"); // 2018-01-12 (no need ja/JP)
            
//          System.setProperty("drools.datetimeformat", "yyyy-MMM-dd HH:mm:ss"); // not used

            
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession();

            // go !
            Person john = new Person("john", 20, new Date());
            kSession.insert(john);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

package com.sample;

import java.text.SimpleDateFormat;
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
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
            
        	KieSession kSession = kContainer.newKieSession("ksession1");

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            
            Message message1 = new Message(1, sdf.parse("01-01-2016 10:00:00.000"), "AAA");
            Message message2 = new Message(2, sdf.parse("01-03-2017 10:00:00.000"), "BBB");
            Message message3 = new Message(3, sdf.parse("01-11-2017 10:00:00.000"), "CCC");
            Message message4 = new Message(4, sdf.parse("01-12-2017 10:00:00.000"), "DDD");
            Message message5 = new Message(5, sdf.parse("01-04-2018 10:00:00.000"), "EEE");

            kSession.insert(message1);
            kSession.insert(message2);
            kSession.insert(message3);
            kSession.insert(message4);
            kSession.insert(message5);

            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

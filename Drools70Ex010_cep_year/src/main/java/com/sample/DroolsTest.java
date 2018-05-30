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
            
            kSession.setGlobal("lastYear", sdf.parse("01-01-2017 00:00:00.000"));
            kSession.setGlobal("thisYear", sdf.parse("01-01-2018 00:00:00.000"));

            
            Message message1 = new Message(1, sdf.parse("01-01-2016 10:00:00.000"), "AAA");
            Message message2 = new Message(2, sdf.parse("01-01-2017 10:00:00.000"), "BBB");
            Message message3 = new Message(3, sdf.parse("01-02-2017 10:00:00.000"), "CCC");
            Message message4 = new Message(4, sdf.parse("01-03-2017 10:00:00.000"), "DDD");
            Message message5 = new Message(5, sdf.parse("01-04-2017 10:00:00.000"), "EEE");
            Message message6 = new Message(6, sdf.parse("01-07-2017 10:00:00.000"), "FFF");
            Message message7 = new Message(7, sdf.parse("01-11-2017 10:00:00.000"), "GGG");
            Message message8 = new Message(8, sdf.parse("01-03-2018 10:00:00.000"), "HHH");

            kSession.insert(message1);
            kSession.insert(message2);
            kSession.insert(message3);
            kSession.insert(message4);
            kSession.insert(message5);
            kSession.insert(message6);
            kSession.insert(message7);
            kSession.insert(message8);

            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

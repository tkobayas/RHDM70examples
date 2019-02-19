package com.sample;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
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

            KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(kSession, "mylogger");
            
            // go !
            Person john = new Person("john", 20);
            kSession.insert(john);
            kSession.fireAllRules();

            kSession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

package com.sample;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testContainer() {
        try {
            KieServices ks = KieServices.Factory.get();
            ReleaseId releaseId = ks.newReleaseId("com.sample", "Drools73Ex055_basic_kjar", "1.0.0-SNAPSHOT");
            KieContainer kContainer = ks.newKieContainer(releaseId);
            KieScanner kScanner = ks.newKieScanner(kContainer);
            KieSession kSession = kContainer.newKieSession();
            
            kScanner.start(10000L);
            
            for (int i = 0; i < 1000; i++) {
                Person john = new Person("john", 20);
                kSession.insert(john);
                kSession.fireAllRules();

                Thread.sleep(1000L);
            }



            kSession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

package com.sample;

import static org.junit.Assert.assertEquals;

import org.drools.core.command.assertion.AssertEquals;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testContainer() {
        try {
            
            // Enable backward compatibility.
            // See https://docs.jboss.org/drools/release/7.18.0.Final/drools-docs/html_single/#_groupdrlsinkiebasesbyfolderoption
            
            // System.setProperty("drools.groupDRLsInKieBasesByFolder", "true");
            
            KieServices ks = KieServices.Factory.get();
            ReleaseId releaseId = ks.newReleaseId("com.sample", "Drools73Ex050_basic_kjar", "1.0.0-SNAPSHOT");
            KieContainer kContainer = ks.newKieContainer(releaseId);
            KieSession kSession = kContainer.newKieSession();

            Person john = new Person("john", 20);
            kSession.insert(john);
            int fired = kSession.fireAllRules();

            assertEquals(1, fired);
            
            kSession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

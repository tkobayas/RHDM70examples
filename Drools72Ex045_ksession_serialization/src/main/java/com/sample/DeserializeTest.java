package com.sample;

import java.io.FileInputStream;
import java.io.IOException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DeserializeTest {

    public static final void main(String[] args) {
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase();
        KieSession kSession = null;

        // Deserialize
        try (FileInputStream in = new FileInputStream("./ksession.out")) {
            
            kSession = ks.getMarshallers().newMarshaller( kBase ).unmarshall( in );

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // go !
        Person paul = new Person("Paul", 30);
        kSession.insert(paul);
        kSession.fireAllRules();

        kSession.dispose();

    }

}

package com.sample;

import java.io.FileOutputStream;
import java.io.IOException;

import org.drools.core.common.DroolsObjectOutputStream;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class SerializeTest {

    public static final void main(String[] args) {
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession();

        // go !
        Person john = new Person("John", 10);
        kSession.insert(john);
        
        kSession.fireAllRules();

        // Serialize
        try (FileOutputStream out = new FileOutputStream("./ksession.out");
                DroolsObjectOutputStream doos = new DroolsObjectOutputStream(out)) {

            doos.writeObject(kSession);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("=== finished");

    }

}

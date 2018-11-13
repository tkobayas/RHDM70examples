package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RunKjar { 
    
    private static String GROUP_ID = "com.example";
    private static String ARTIFACT_ID = "my-example-a";
    private static String VERSION = "1.0.0";

    public static final void main(String[] args) {
        try {
            // KieServices is the entry point of APIs
            KieServices ks = KieServices.Factory.get();

            ReleaseId releaseId = ks.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
            KieContainer kcontainer = ks.newKieContainer(releaseId);

            // KieContainer can create a KieBase
            KieBase kbase = kcontainer.getKieBase();
            
            // KieBase can create a KieSession
            KieSession ksession = kbase.newKieSession();

            // Now, run the rules
            Person john = new Person("John", 32);
            ksession.insert(john);
            ksession.fireAllRules();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

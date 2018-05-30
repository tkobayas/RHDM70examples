package com.sample;

import java.io.File;
import java.io.FileInputStream;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // KieServices is the entry point of APIs
            KieServices ks = KieServices.Factory.get();

            // KieFileSystem is responsible for gathering resources
            KieFileSystem kfs = ks.newKieFileSystem();

            // You can add your DRL files as InputStream here
            kfs.write("src/main/resources/rules/Sample1.drl",
                    ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample1.drl"))));
            kfs.write("src/main/resources/12345.drl",
                    ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample2.drl"))));
            
            // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
            // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
            ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
            kfs.generateAndWritePomXML(releaseId);

            // Now resources are built and stored into an internal repository
            ks.newKieBuilder( kfs ).buildAll();

            // You can get a KieContainer with the ReleaseId
            KieContainer kcontainer = ks.newKieContainer(releaseId);

            // KieContainer can create a KieBase
            KieBase kbase = kcontainer.getKieBase();
            
            // KieBase can create a KieSession
            KieSession ksession = kbase.newKieSession();

            // Now, run the rules
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            ksession.insert(message);
            ksession.fireAllRules();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}

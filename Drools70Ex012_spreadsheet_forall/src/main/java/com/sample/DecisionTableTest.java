package com.sample;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DecisionTableTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();

            // useful for debug
            SpreadsheetCompiler compiler = new SpreadsheetCompiler();
            String drl = compiler.compile(ks.getResources().newClassPathResource("dtables/Sample.xls").getInputStream(), InputType.XLS);
            System.out.println(drl);

            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-dtables");

            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(0);
            kSession.insert(message);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

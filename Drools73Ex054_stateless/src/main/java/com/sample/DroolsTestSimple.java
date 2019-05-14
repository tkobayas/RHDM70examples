package com.sample;

import java.util.Arrays;
import java.util.List;

import org.drools.core.common.InternalFactHandle;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTestSimple {

    public static final void main(String[] args) {
        try {

            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            StatelessKieSession kSession = kContainer.newStatelessKieSession();

            Person john = new Person("john", 20);

            // Insert facts and fire
            Command insertElementsCommand = CommandFactory.newInsertElements(Arrays.asList(john));
            List<InternalFactHandle> factHandleList = (List<InternalFactHandle>) kSession.execute(insertElementsCommand);

            factHandleList.stream().forEach(fh -> System.out.println(fh.getObject()));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

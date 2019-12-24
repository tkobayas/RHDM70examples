package com.sample;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.compiler.RuntimeTypeCheckOption;
import org.kie.dmn.core.impl.DMNRuntimeImpl;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kieContainer = ks.getKieClasspathContainer();

            DMNRuntime runtime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
            ((DMNRuntimeImpl) runtime).setOption(new RuntimeTypeCheckOption(true));

            DMNModel dmnModel = runtime.getModel("https://github.com/kiegroup/drools/kie-dmn/_B744A985-7C00-4D5B-91E0-59EF9656B083", "SimpleDecision");

            System.out.println(dmnModel);

            {
                System.out.println("============================");

                DMNContext context = DMNFactory.newContext();
                Map<String, Object> person = new HashMap<>();
                person.put("name", "John");
                person.put("age", 18);
                context.set("Person", person);

                System.out.println("context = " + context);

                DMNResult dmnResult = runtime.evaluateAll(dmnModel, context);

                DMNContext result = dmnResult.getContext();

                System.out.println("result context = " + result);

                System.out.println("RESULT = " + result.get("RESULT"));

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

package com.sample;

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

            DMNModel dmnModel = runtime.getModel("http://www.trisotech.com/dmn/definitions/_982cbb06-9460-416c-a72b-64ec442d0818", "my-example01");

            System.out.println("dmnModel = " + dmnModel);
            {
 
                DMNContext context = DMNFactory.newContext();
                context.set("Person", "John");

                DMNResult dmnResult = runtime.evaluateAll(dmnModel, context);

                DMNContext result = dmnResult.getContext();

                System.out.println(result.get("Check Person"));
            }

            {
                DMNContext context = DMNFactory.newContext();

                context.set("Person", "Paul");

                DMNResult dmnResult = runtime.evaluateAll(dmnModel, context);

                DMNContext result = dmnResult.getContext();

                System.out.println(result.get("Check Person"));
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

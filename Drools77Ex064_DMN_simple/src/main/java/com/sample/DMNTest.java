package com.sample;

import java.util.UUID;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.compiler.RuntimeTypeCheckOption;
import org.kie.dmn.core.impl.DMNRuntimeImpl;
import org.kie.dmn.core.util.KieHelper;

/**
 * This is a sample class to launch a rule.
 */
public class DMNTest {

    public static final void main(String[] args) {
        final KieServices ks = KieServices.Factory.get();
        final KieContainer kieContainer = KieHelper.getKieContainer(
                ks.newReleaseId("org.kie", "dmn-test-"+UUID.randomUUID(), "1.0"),
                ks.getResources().newClassPathResource("all-elements.dmn", DMNTest.class));

        DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
        ((DMNRuntimeImpl) dmnRuntime).setOption(new RuntimeTypeCheckOption(true));
        
        final DMNModel dmnModel = dmnRuntime.getModel("https://kiegroup.org/dmn/_88969B73-EFCB-4382-9112-2347A8367E43", "all-elements" );

        System.out.println("dmnModel = " + dmnModel);

        final DMNContext context = DMNFactory.newContext();
        context.set( "InputData-1", "XXX" );

        final DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, context );

        System.out.println(dmnResult);
    }

}

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
            
            DMNModel dmnModel = runtime.getModel( "https://github.com/kiegroup/kie-dmn/itemdef", "simple-item-def" );

            System.out.println(dmnModel);
            
            DMNContext context = DMNFactory.newContext();
            context.set( "Monthly Salary", 1000 );

            DMNResult dmnResult = runtime.evaluateAll( dmnModel, context );

            DMNContext result = dmnResult.getContext();
            
            System.out.println(result.get( "Yearly Salary"));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}

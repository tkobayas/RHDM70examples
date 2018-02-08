package com.github.tkobayas.parallel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.github.tkobayas.util.ReteDumper;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.conf.MultithreadEvaluationOption;
import org.kie.internal.utils.KieHelper;
import org.openjdk.jmh.runner.RunnerException;

public class SimpleMain1 {

    private static final int NUM_OF_RULES = 10;

    public static void main(String[] args) throws RunnerException, IOException {

        final KieBaseConfiguration kieBaseConfiguration = KieServices.Factory.get().newKieBaseConfiguration();
        kieBaseConfiguration.setOption(MultithreadEvaluationOption.YES);
        final KieHelper kieHelper = new KieHelper();
        String drl = generateDrl();
        kieHelper.addContent(drl, ResourceType.DRL);
        KieBase kieBase = kieHelper.build(kieBaseConfiguration);

        ReteDumper.dumpRete(kieBase);

        KieSession kieSession = kieBase.newKieSession();
        kieSession.setGlobal("list", Collections.synchronizedList(new ArrayList<Integer>()));
        
        kieSession.insert(1);
        kieSession.insert("1");
        
        int result = kieSession.fireAllRules();
        System.out.println("---- number of fired rules = " + result);

        kieSession.dispose();

    }

    private static String generateDrl() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("package com.github.tkobayas.parallel\n\n");
        sb.append("global java.util.List list;\n\n");
        
        for (int i = 0; i < NUM_OF_RULES; i++) {
            sb.append("rule R" + i + "\n"
                    + "when\n"
                    + "    $i : Integer( intValue == " + i + " )\n"
                    + "    String( toString == $i.toString )\n"
                    + "then\n"
                    + "    list.add($i);\n"
                    + "end\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}

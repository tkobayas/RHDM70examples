package com.sample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.definitions.rule.impl.RuleImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.ruleunit.RuleUnitDescription;
import org.drools.core.ruleunit.RuleUnitDescriptionRegistry;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.pmml.PMML4Data;
import org.kie.api.pmml.PMML4Result;
import org.kie.api.pmml.PMMLRequestData;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.DataSource;
import org.kie.api.runtime.rule.RuleUnit;
import org.kie.api.runtime.rule.RuleUnitExecutor;
import org.kie.pmml.pmml_4_2.PMML4Compiler;
import org.kie.pmml.pmml_4_2.model.PMML4UnitImpl;

public class DroolsTest {

    @Test
    public void testContainer() {
        try {

            //DTtoDRL("./src/main/resources/test_scorecard_compound_predicate.pmml");
            
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.newKieClasspathContainer();
            KieBase kbase = kContainer.getKieBase();

            RuleUnitExecutor executor = RuleUnitExecutor.create().bind(kbase);
            DataSource<PMMLRequestData> requestData = executor.newDataSource("request");
            DataSource<PMML4Result> resultData = executor.newDataSource("results");
            DataSource<PMML4Data> internalData = executor.newDataSource("pmmlData");

            String correlationId = "id";
            String modelName = "ScorecardCompoundPredicate";
            String modelPkgName = "org.drools.scorecards.example";

            // インプットデータの生成
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("param1", 20d);
            variables.put("param2", 10d);

            PMMLRequestData request = new PMMLRequestData(correlationId, modelName);
            variables.entrySet().forEach(es -> {
                request.addRequestParam(es.getKey(), es.getValue());
            });
            PMML4Result resultHolder = new PMML4Result(correlationId);

            requestData.insert(request);
            resultData.insert(resultHolder);

            List<String> possiblePackageNames = calculatePossiblePackageNames(modelName, modelPkgName);
            // "RuleUnitIndicator"は固定値
            Class<? extends RuleUnit> ruleUnitClass = getStartingRuleUnit("RuleUnitIndicator", (InternalKnowledgeBase) kbase, possiblePackageNames);

            if (ruleUnitClass != null) {
                // ここで実行
                executor.run(ruleUnitClass);
                System.out.println("===>result");
                if ("OK".equals(resultHolder.getResultCode())) {
                    // 結果をコンソール出力
                    System.out.println(resultHolder.getResultVariables().toString());
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    protected static Class<? extends RuleUnit> getStartingRuleUnit(String startingRule, InternalKnowledgeBase ikb, List<String> possiblePackages) {
        RuleUnitDescriptionRegistry unitRegistry = ikb.getRuleUnitDescriptionRegistry();
        Map<String, InternalKnowledgePackage> pkgs = ikb.getPackagesMap();
        RuleImpl ruleImpl = null;
        for (String pkgName : possiblePackages) {
            if (pkgs.containsKey(pkgName)) {
                InternalKnowledgePackage pkg = pkgs.get(pkgName);
                ruleImpl = pkg.getRule(startingRule);
                if (ruleImpl != null) {
                    RuleUnitDescription descr = unitRegistry.getDescription(ruleImpl).orElse(null);
                    if (descr != null) {
                        return descr.getRuleUnitClass();
                    }
                }
            }
        }
        return null;
    }

    protected static List<String> calculatePossiblePackageNames(String modelId, String... knownPackageNames) {
        List<String> packageNames = new ArrayList<>();
        String javaModelId = modelId.replaceAll("\\s", "");
        if (knownPackageNames != null && knownPackageNames.length > 0) {
            for (String knownPkgName : knownPackageNames) {
                packageNames.add(knownPkgName + "." + javaModelId);
            }
        }
        String basePkgName = PMML4UnitImpl.DEFAULT_ROOT_PACKAGE + "." + javaModelId;
        packageNames.add(basePkgName);
        return packageNames;
    }

    public static void DTtoDRL(String pmmlpath) {

        try (InputStream is = new FileInputStream(pmmlpath)) {
            PMML4Compiler compiler = new PMML4Compiler();
            String drl = compiler.compile(is, Class.class.getClassLoader());
            // DRLにコンパイルした結果をコンソール出力
            System.out.println(drl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

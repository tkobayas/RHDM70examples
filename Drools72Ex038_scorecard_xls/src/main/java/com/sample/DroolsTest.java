package com.sample;

import org.dmg.pmml.pmml_4_2.descr.PMML;
import org.kie.pmml.pmml_4_2.PMML4Compiler;
import org.drools.scorecards.ScorecardCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.drools.scorecards.ScorecardCompiler.DrlType.INTERNAL_DECLARED_TYPES;
import static org.junit.Assert.assertEquals;


/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            
//            dumpDRL();
            
            doTest();
            


            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    
    private static void doTest() throws InstantiationException, IllegalAccessException {
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession();
        KieBase kieBase = kSession.getKieBase();
//
//        // go !
        FactType scorecardType = kieBase.getFactType( "org.drools.scorecards.example","SampleScore" );
        Object scorecard = scorecardType.newInstance();
        scorecardType.set(scorecard, "age", 10);
        kSession.insert( scorecard );
        kSession.fireAllRules();
        kSession.dispose();
        //occupation = 5, age = 25, validLicence -1
        assertEquals( 29.0, scorecardType.get( scorecard, "scorecard__calculatedScore" ) );
        System.out.println("calculatedScore = " + scorecardType.get( scorecard, "scorecard__calculatedScore" ));

        kSession = kieBase.newKieSession();
        scorecard = scorecardType.newInstance();
        scorecardType.set(scorecard, "occupation", "SKYDIVER");
        scorecardType.set(scorecard, "age", 0);
        kSession.insert( scorecard );
        kSession.fireAllRules();
        kSession.dispose();
        //occupation = -10, age = +10, validLicense = -1;
        assertEquals( -1.0, scorecardType.get( scorecard, "scorecard__calculatedScore" ) );
        System.out.println("calculatedScore = " + scorecardType.get( scorecard, "scorecard__calculatedScore" ));

        kSession = kieBase.newKieSession();
        scorecard = scorecardType.newInstance();
        scorecardType.set(scorecard, "residenceState", "AP");
        scorecardType.set(scorecard, "occupation", "TEACHER");
        scorecardType.set(scorecard, "age", 20);
        scorecardType.set(scorecard, "validLicense", true);
        kSession.insert( scorecard );
        kSession.fireAllRules();
        kSession.dispose();
        //occupation = +10, age = +40, state = -10, validLicense = 1
        assertEquals( 41.0, scorecardType.get( scorecard, "scorecard__calculatedScore" ) );
        System.out.println("calculatedScore = " + scorecardType.get( scorecard, "scorecard__calculatedScore" ));
    }


    public static void dumpDRL() throws Exception {
        ScorecardCompiler scorecardCompiler = new ScorecardCompiler(INTERNAL_DECLARED_TYPES);
        if (scorecardCompiler.compileFromExcel(DroolsTest.class.getResourceAsStream("/rules/scoremodel_c.sxls")) ) {
            PMML pmmlDocument = scorecardCompiler.getPMMLDocument();
            System.out.println(pmmlDocument);
            PMML4Compiler.dumpModel( pmmlDocument, System.out );
            
            System.out.println("--------------------------------------");
            
            String drl = scorecardCompiler.getDRL();
            System.out.println(drl);
        } else {
            System.out.println("failed to parse scoremodel Excel.");
        }
    }

}

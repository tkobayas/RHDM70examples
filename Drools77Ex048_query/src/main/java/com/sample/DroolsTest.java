package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main( String[] args ) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();

            FactA factA = new FactA( "ABC" );
            kSession.insert( factA );

            kSession.getAgenda().getAgendaGroup("mygroup").setFocus();
            kSession.fireAllRules();

            QueryResults results = kSession.getQueryResults("query1");

            System.out.println( "results.size() = " + results.size() );
            for (QueryResultsRow queryResultsRow : results) {
                FactB factB = (FactB)queryResultsRow.get("$b");
                System.out.println("factB = " + factB);
            }

            
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

}

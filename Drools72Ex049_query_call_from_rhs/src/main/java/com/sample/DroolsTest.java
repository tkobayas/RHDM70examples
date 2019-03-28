package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main( String[] args ) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession( "ksession-rules" );

            Person p1 = new Person( "John", 31 );
            Person p2 = new Person( "Paul", 32 );
            Person p3 = new Person( "George", 26 );
            kSession.insert( p1 );
            kSession.insert( p2 );
            kSession.insert( p3 );

//            QueryResults results = kSession.getQueryResults("query1");
//            System.out.println( "results.size() = " + results.size() );
            
            kSession.fireAllRules();
            
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

}

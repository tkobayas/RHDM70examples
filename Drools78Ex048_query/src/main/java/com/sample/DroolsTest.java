package com.sample;

import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Query;
import org.kie.api.definition.rule.Rule;
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
//            KieSession kSession = kContainer.newKieSession( "ksession-rules" );
            KieSession kSession = kContainer.newKieSession( );

            KieBase kieBase = kSession.getKieBase();
            Collection<KiePackage> kiePackages = kieBase.getKiePackages();
            for (KiePackage kiePackage : kiePackages) {
                Collection<Rule> rules = kiePackage.getRules();
                System.out.println(rules);
                Collection<Query> queries = kiePackage.getQueries();
                System.out.println(queries);
            }
            
            

            System.out.println("---");
            
            Person p1 = new Person( "John", 31 );
            Person p2 = new Person( "Paul", 32 );
            Person p3 = new Person( "George", 26 );
            kSession.insert( p1 );
            kSession.insert( p2 );
            kSession.insert( p3 );

            QueryResults results = kSession.getQueryResults("query1");
            System.out.println( "results.size() = " + results.size() );
            
            kSession.fireAllRules();
            
//            QueryResults results = kSession.getQueryResults("query1");
//            System.out.println( "results.size() = " + results.size() );
            
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

}

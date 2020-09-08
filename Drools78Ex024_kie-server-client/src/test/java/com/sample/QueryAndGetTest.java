package com.sample;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;
import org.drools.core.runtime.rule.impl.FlatQueryResults;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class QueryAndGetTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    
    private static final String KSESSION_NAME = "myStatefulKsession";
    
//        private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
      private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;
    //private static final MarshallingFormat FORMAT = MarshallingFormat.XSTREAM;

    @Test
    public void testProcess() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Person.class);

        config.addExtraClasses(classes);
        
        config.setMarshallingFormat(FORMAT);
        
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
        
        Person person = new Person("JohnXXYZ", 20);
        
        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        commands.add(commandsFactory.newInsert(person, "fact-" + person.getName()));

        commands.add(commandsFactory.newFireAllRules("fire-result"));
        
        commands.add(commandsFactory.newQuery("query-result1", "query1"));
//        commands.add(commandsFactory.newQuery(null, "query1"));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);
        
        Object value = response.getResult().getValue("query-result1");
        
        System.out.println(value);
        System.out.println(value.getClass());

        FlatQueryResults flatQueryResults = (FlatQueryResults)value;
        
        FactHandle lastFactHandle = null;
        
        for (QueryResultsRow queryResultsRow : flatQueryResults) {
            System.out.println("------");
            Object object = queryResultsRow.get("$p");
            System.out.println("object : " + object);
            FactHandle factHandle = queryResultsRow.getFactHandle("$p");
            System.out.println("factHandle : " + factHandle);
            
            lastFactHandle = factHandle;
        }
        
        //--------------------
        
        commands.clear();
        
        commands.add(commandsFactory.newGetObject(lastFactHandle, "get-obj"));

        batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response2 = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);
        
        System.out.println(response2);
        
    }
}
package com.sample;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class SimpleTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    
    private static final String KSESSION_NAME = "myStatefulKsession";
    
        private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    //  private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;
    //private static final MarshallingFormat FORMAT = MarshallingFormat.XSTREAM;

    @Test
    public void testProcess() {
        
        // 1st run
        runClient(new Person("John", 20)); // results = [hit Hello rule]
        
        // don't forget dispose
        //dispose();
    }

    private RuleServicesClient getRuleClient() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Person.class);

        config.addExtraClasses(classes);
        
        config.setMarshallingFormat(FORMAT);
        
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
        return ruleClient;
    }
    
    private void runClient(Person person) {
        
//        person.setNewProp("XXX");
        
        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        
        commands.add(commandsFactory.newInsert(person, "fact-" + person.getName()));

        commands.add(commandsFactory.newFireAllRules("fire-result"));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);
    }
    
    private void dispose() {
        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        
        commands.add(commandsFactory.newDispose());

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);

    }

}
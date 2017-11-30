

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.server.client.impl.ProcessServicesClientImpl;

public class SimpleAbort {

    private static final String BASE_URL = "http://localhost:8080/kie-execution-server/services/rest/server";
    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";

    private static final String COMTAINER_ID = "org.kie.example:project1:1.0.0-SNAPSHOT";

    
    public static void main(String[] args) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
//        classes.add(MyPojo.class);
        config.addJaxbClasses(classes);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient proessClient = client.getServicesClient(ProcessServicesClient.class);

        proessClient.abortProcessInstance(COMTAINER_ID, 61L);

    }
}

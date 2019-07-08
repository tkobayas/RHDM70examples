package com.sample;

import static com.sample.Constants.BASE_URL;

import java.util.HashSet;

import junit.framework.TestCase;
import optacloud.optacloud.CloudSolution;
import org.junit.Test;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.SolverServicesClient;

public class SolverDisposeTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";

    private static final String CONTAINER_1_ID = "optacloud_1.0.0-SNAPSHOT";
    private static final String SOLVER_1_ID = "solver-1";
    private static final String SOLVER_1_CONFIG = "optacloud/optacloud/cloudSolverConfig.solver.xml";

    private static final String CLASS_CLOUD_BALANCE = "optacloud.optacloud.CloudSolution";
    private static final String CLASS_CLOUD_COMPUTER = "optacloud.optacloud.Computer";
    private static final String CLASS_CLOUD_PROCESS = "optacloud.optacloud.Process";
    
    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;

    @Test
    public void testProcess() {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        //        config.setMarshallingFormat(FORMAT);
        HashSet<Class<?>> extraClasses = new HashSet<Class<?>>();
        extraClasses.add(optacloud.optacloud.CloudSolution.class);
        extraClasses.add(optacloud.optacloud.Computer.class);
        extraClasses.add(optacloud.optacloud.Process.class);
        config.addExtraClasses(extraClasses);
        
        config.setTimeout(600000);
        
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        SolverServicesClient solverClient = client.getServicesClient(SolverServicesClient.class);
        
        solverClient.disposeSolver(CONTAINER_1_ID,
                                   SOLVER_1_ID);
    }



}
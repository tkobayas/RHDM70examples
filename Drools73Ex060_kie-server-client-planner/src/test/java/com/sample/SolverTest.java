package com.sample;

import static com.sample.Constants.BASE_URL;

import java.util.ArrayList;
import java.util.HashSet;

import junit.framework.TestCase;
import optacloud.optacloud.CloudSolution;
import optacloud.optacloud.Computer;
import org.junit.Test;
import org.kie.server.api.model.instance.ScoreWrapper;
import org.kie.server.api.model.instance.SolverInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.SolverServicesClient;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public class SolverTest extends TestCase {

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
        
        // Prerequisit: Build&Deploy "OptaCloud" example in decision-central in RHDM 7.3.0

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

        solverClient.createSolver(CONTAINER_1_ID, SOLVER_1_ID, SOLVER_1_CONFIG);
        
        System.out.println(" ==== solver is created");

        Object planningProblem = loadPlanningProblem();
        solverClient.solvePlanningProblem(CONTAINER_1_ID, SOLVER_1_ID, planningProblem);
        
        System.out.println(" ==== waiting for solving");
        
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(" ==== going to get best solution");

        SolverInstance solverInstance = solverClient.getSolverWithBestSolution(CONTAINER_1_ID, SOLVER_1_ID);
        assertNotNull(solverInstance);
        Object solution = solverInstance.getBestSolution();
        System.out.println("solution = " + solution);

        ScoreWrapper scoreWrapper = solverInstance.getScoreWrapper();
        assertNotNull(scoreWrapper);

        if (scoreWrapper.toScore() != null) {
            assertEquals(HardSoftScore.class, scoreWrapper.getScoreClass());
            HardSoftScore score = (HardSoftScore) scoreWrapper.toScore();
            System.out.println("score = " + score);
        }

        solverClient.disposeSolver(CONTAINER_1_ID, SOLVER_1_ID);
    }

    private Object loadPlanningProblem() {
        CloudSolution cloudSolution = new CloudSolution();
        ArrayList<Computer> computerList = new ArrayList<optacloud.optacloud.Computer>();
        for (int i = 0; i < 10; i++) {
            Computer computer = new Computer(4, 4, 4, 100);
            computerList.add(computer);
        }
        ArrayList<optacloud.optacloud.Process> processList = new ArrayList<optacloud.optacloud.Process>();
        for (int i = 0; i < 10; i++) {
            optacloud.optacloud.Process process = new optacloud.optacloud.Process(2, 2, 2, null);
            processList.add(process);
        }
        cloudSolution.setComputerList(computerList);
        cloudSolution.setProcessList(processList);

        return cloudSolution;
    }

}
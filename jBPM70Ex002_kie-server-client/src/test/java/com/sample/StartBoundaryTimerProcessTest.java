package com.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import junit.framework.TestCase;

public class StartBoundaryTimerProcessTest extends TestCase {
    
    public static final String CONTAINER_ID = "org.kie.example:project1:1.0.0-SNAPSHOT";

    
	public void testRest() throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		ProcessServicesClient processClient = KieServerRestUtils.getProcessServicesClient();
		long processInstanceId = processClient.startProcess(CONTAINER_ID, "project1.helloBoundaryTimer", params);

		System.out.println("startProcess() : processInstanceId = " + processInstanceId);

	}
}
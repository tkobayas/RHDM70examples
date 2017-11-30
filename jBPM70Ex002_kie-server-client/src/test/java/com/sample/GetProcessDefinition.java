package com.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.TaskEventInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import junit.framework.TestCase;

public class GetProcessDefinition extends TestCase {

    public static final String CONTAINER_ID = "org.kie.example:project1:1.0.0-SNAPSHOT";

    public void testRest() throws Exception {

        ProcessServicesClient processServiceClient = KieServerRestUtils.getProcessServicesClient();
        
        ProcessDefinition processDefinition = processServiceClient.getProcessDefinition(CONTAINER_ID, "project1.helloTimer");
        System.out.println(new XStream().toXML(processDefinition));
    }
}
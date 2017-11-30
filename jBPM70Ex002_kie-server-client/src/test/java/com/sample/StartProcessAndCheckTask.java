package com.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.TaskEventInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import junit.framework.TestCase;

public class StartProcessAndCheckTask extends TestCase {

    public static final String CONTAINER_ID = "org.kie.example:project1:1.0.0-SNAPSHOT";

    public void testRest() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        ProcessServicesClient processClient = KieServerRestUtils.getProcessServicesClient();
        long processInstanceId = processClient.startProcess(CONTAINER_ID, "project1.helloTimer", params);

        System.out.println("startProcess() : processInstanceId = " + processInstanceId);

        //        QueryServicesClient queryClient = KieServerUtils.getQueryServicesClient("bpmsAdmin", "password1!");

                UserTaskServicesClient taskClient = KieServerRestUtils.getUserTaskServicesClient("bpmsAdmin", "password1!");
                
                List<TaskSummary> findTasks = taskClient.findTasks("bpmsAdmin", 0, 1);
                Long taskId = findTasks.get(0).getId();
                
                TaskInstance taskInstance = taskClient.getTaskInstance(CONTAINER_ID, taskId);
                System.out.println("taskInstance.getWorkItemId() = " + taskInstance.getWorkItemId());
        
        //        List<TaskEventInstance> findTaskEvents = taskClient.findTaskEvents(taskId, 0, 1);
        //        System.out.println(findTaskEvents);

//        ProcessServicesClient processServiceClient = KieServerUtils.getProcessServicesClient();
//        
//        ProcessDefinition processDefinition = processServiceClient.getProcessDefinition(CONTAINER_ID, "project1.helloTimer");
//        System.out.println(processDefinition);
    }
}
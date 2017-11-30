package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.kie.server.api.KieServerConstants;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;

public class KieServerJmsUtils {

    private static final String BASE_URL = "http://localhost:8080/kie-execution-server/services/rest/server";
    private static final String DEFAULT_USERNAME = "kieserver";
    private static final String DEFAULT_PASSWORD = "kieserver1!";

    private static final String JMS_USERNAME = "bpmsAdmin";
    private static final String JMS_PASSWORD = "password1!";

    private static KieServicesConfiguration getJMSConfiguration(String username, String password) {
        try {
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            env.put(Context.PROVIDER_URL, "remote://localhost:4447");
            env.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
            env.put(Context.SECURITY_CREDENTIALS, JMS_PASSWORD);
            InitialContext context = new InitialContext(env);

            Queue requestQueue = (Queue) context.lookup("jms/queue/KIE.SERVER.REQUEST");
            Queue responseQueue = (Queue) context.lookup("jms/queue/KIE.SERVER.RESPONSE");
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");

            return KieServicesFactory.newJMSConfiguration(
                    connectionFactory, requestQueue, responseQueue, username,
                    password);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static KieServicesClient getKieServicesClient(String username, String password) {

        KieServicesConfiguration config = getJMSConfiguration(username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        return client;
    }

    public static UserTaskServicesClient getUserTaskServicesClient() {
        return getUserTaskServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static UserTaskServicesClient getUserTaskServicesClient(String username, String password) {

        KieServicesConfiguration config = getJMSConfiguration(username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        UserTaskServicesClient userTaskServiceClient = client.getServicesClient(UserTaskServicesClient.class);

        return userTaskServiceClient;
    }

    public static QueryServicesClient getQueryServicesClient() {
        return getQueryServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static QueryServicesClient getQueryServicesClient(String username, String password) {

        KieServicesConfiguration config = getJMSConfiguration(username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        QueryServicesClient queryServiceClient = client.getServicesClient(QueryServicesClient.class);

        return queryServiceClient;
    }

    public static ProcessServicesClient getProcessServicesClient() {
        return getProcessServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static ProcessServicesClient getProcessServicesClient(String username, String password) {

        KieServicesConfiguration config = getJMSConfiguration(username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient processServiceClient = client.getServicesClient(ProcessServicesClient.class);

        return processServiceClient;
    }

    public static RuleServicesClient getRuleServicesClient() {
        return getRuleServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static RuleServicesClient getRuleServicesClient(String username, String password) {

        KieServicesConfiguration config = getJMSConfiguration(username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BRM);
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        config.setTimeout(60000);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        RuleServicesClient ruleServiceClient = client.getServicesClient(RuleServicesClient.class);

        return ruleServiceClient;
    }
}

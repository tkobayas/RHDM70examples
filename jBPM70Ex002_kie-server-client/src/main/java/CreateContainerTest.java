

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;

public class CreateContainerTest {

	public static void main(String[] args) {
		KieServicesConfiguration config = KieServicesFactory
				.newRestConfiguration(
						"http://localhost:8080/kie-execution-server/services/rest/server",
						"kieserver", "kieserver1!");
		KieServicesClient client = KieServicesFactory
				.newKieServicesClient(config);
		
        ReleaseId releaseId = new ReleaseId("org.kie.example", "project1", "1.0.0-SNAPSHOT");
        KieContainerResource resource = new KieContainerResource("org.kie.example:project1:1.0.0-SNAPSHOT", releaseId);
		ServiceResponse<KieContainerResource> response = client.createContainer("org.kie.example:project1:1.0.0-SNAPSHOT", resource);

		System.out.println(response);
	}
}



import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class KieRestClientBasicTest {

	public static void main(String[] args) {
		KieServicesConfiguration config = KieServicesFactory
				.newRestConfiguration(
						"http://localhost:8080/kie-execution-server/services/rest/server/",
						"kieserver", "kieserver1!");
		KieServicesClient client = KieServicesFactory
				.newKieServicesClient(config);
		ServiceResponse<KieServerInfo> response = client.getServerInfo();
		System.out.println(response.getResult());
	}
}

package org.springframework.cloud.servicebroker.cloudkarafka.fixture;

import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstanceBinding;

import java.util.Collections;
import java.util.Map;

public class ServiceInstanceBindingFixture {
    public static ServiceInstanceBinding getServiceInstanceBinding() {
        final Map<String, Object> credentials = Collections.singletonMap("url", (Object) "kafka://example.com");
        return new ServiceInstanceBinding("binding-id", "service-instance-id", credentials, null, "app-guid");
    }
}

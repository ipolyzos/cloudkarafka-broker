package org.springframework.cloud.servicebroker.cloudkarafka.fixture;

import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstance;

public class ServiceInstanceFixture {
    public static ServiceInstance getServiceInstance() {
        return new ServiceInstance("service-instance-id", "service-definition-id", "plan-id",
                "org-guid", "space-guid", "http://dashboard.example.com");
    }
}

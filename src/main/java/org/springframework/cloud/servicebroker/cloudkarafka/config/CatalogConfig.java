package org.springframework.cloud.servicebroker.cloudkarafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.servicebroker.cloudkarafka.model.proxy.ServiceDefinitionProxy;
import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "catalog")
@Configuration
public class CatalogConfig {

    private List<ServiceDefinitionProxy> services;

    public CatalogConfig() {
        super();
    }

    public CatalogConfig(List<ServiceDefinitionProxy> services) {
        super();
        this.services = services;
    }

    @Bean
    public Catalog catalog() {
        return new Catalog(services.stream().map(s -> s.unproxy())
                .collect(Collectors.toList()));
    }

    public List<ServiceDefinitionProxy> getServices() {
        return services;
    }

    public void setServices(List<ServiceDefinitionProxy> services) {
        this.services = services;
    }

    public ServiceDefinitionProxy findServiceDefinition(String serviceId) {
        return services.stream().filter(s -> s.getId().equals(serviceId))
                .findFirst().get();
    }

}
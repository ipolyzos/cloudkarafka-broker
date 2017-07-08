package org.springframework.cloud.servicebroker.cloudkarafka.repository;

import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstanceBinding;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Service Instance Binding objects
 *
 * @author ipolyzos
 */
public interface CloudKarafkaServiceInstanceBindingRepository extends MongoRepository<ServiceInstanceBinding, String> {
}
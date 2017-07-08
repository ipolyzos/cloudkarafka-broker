package org.springframework.cloud.servicebroker.cloudkarafka.repository;

import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Service Instance objects
 *
 * @author ipolyzos
 */
public interface CloudKarafkaServiceInstanceRepository extends MongoRepository<ServiceInstance, String> {
}
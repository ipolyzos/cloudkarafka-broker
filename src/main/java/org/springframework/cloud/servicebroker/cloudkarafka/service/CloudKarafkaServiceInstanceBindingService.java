package org.springframework.cloud.servicebroker.cloudkarafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudkarafka.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudkarafka.exception.CloudKarafkaServiceException;
import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstance;
import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstanceBinding;
import org.springframework.cloud.servicebroker.cloudkarafka.repository.CloudKarafkaServiceInstanceBindingRepository;
import org.springframework.cloud.servicebroker.cloudkarafka.repository.CloudKarafkaServiceInstanceRepository;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Service instance binding service.
 * <p>
 * NOTE:
 * Binding a service does the following:
 * 1. Creates a Kafka instance
 * 2. Retrieve instance credentials
 * 3. Saves the ServiceInstanceBinding info to the CloudKarafka
 * repository along with credential and URI
 *
 * @author ipolyzos
 */
@Service
public class CloudKarafkaServiceInstanceBindingService implements ServiceInstanceBindingService {

    private CloudKarafkaAdminService cloudKarafkaAdminService;

    private BrokerConfig brokerConfig;

    private CloudKarafkaServiceInstanceRepository instanceRepository;

    private CloudKarafkaServiceInstanceBindingRepository bindingRepository;

    @Autowired
    public CloudKarafkaServiceInstanceBindingService(final CloudKarafkaAdminService cloudKarafkaAdminService,
                                                     final BrokerConfig brokerConfig,
                                                     final CloudKarafkaServiceInstanceRepository instanceRepository,
                                                     final CloudKarafkaServiceInstanceBindingRepository bindingRepository) {
        this.cloudKarafkaAdminService = cloudKarafkaAdminService;
        this.brokerConfig = brokerConfig;
        this.instanceRepository = instanceRepository;
        this.bindingRepository = bindingRepository;
    }

    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(final CreateServiceInstanceBindingRequest request) {
        final String bindingId = request.getBindingId();
        final String serviceInstanceId = request.getServiceInstanceId();

        ServiceInstanceBinding binding = bindingRepository.findOne(bindingId);
        if (binding != null) {
            throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
        }

        ServiceInstance instance = instanceRepository.findOne(serviceInstanceId);
        if (instance == null) {
            throw new CloudKarafkaServiceException("Instance don't exist :" + serviceInstanceId);
        }

        final Map<String, Object> credentials = new HashMap<String, Object>(){
            {
                put("brokers", instance.getCloudKarafkaBrokers());
                put("ca", instance.getCloudKarafkaCa());
                put("cert", instance.getCloudKarafkaCert());
                put("id", instance.getCloudKarafkaId());
                put("private_key", instance.getCloudKarafkaPrivateKey());
                put("topic_prefix", instance.getCloudKarafkaTopicPrefix());
                put("brokers",instance.getCloudKarafkaBrokers());
                put("message",instance.getCloudKarafkaMessage());
            }};

        binding = new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, request.getBoundAppGuid());
        bindingRepository.save(binding);

        return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
    }

    @Override
    public void deleteServiceInstanceBinding(final DeleteServiceInstanceBindingRequest request) {
        final String bindingId = request.getBindingId();
        final String serviceInstanceId = request.getServiceInstanceId();

        final ServiceInstanceBinding binding = getServiceInstanceBinding(bindingId);
        if (binding == null) {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }

        bindingRepository.delete(bindingId);
    }

    protected ServiceInstanceBinding getServiceInstanceBinding(final String bindingId) {
        return bindingRepository.findOne(bindingId);
    }
}
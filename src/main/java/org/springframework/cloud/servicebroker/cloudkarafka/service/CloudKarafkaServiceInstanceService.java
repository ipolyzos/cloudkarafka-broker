package org.springframework.cloud.servicebroker.cloudkarafka.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudkarafka.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudkarafka.exception.CloudKarafkaServiceException;
import org.springframework.cloud.servicebroker.cloudkarafka.model.CloudKarafkaCreateInstanceResponse;
import org.springframework.cloud.servicebroker.cloudkarafka.model.ServiceInstance;
import org.springframework.cloud.servicebroker.cloudkarafka.repository.CloudKarafkaServiceInstanceRepository;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceExistsException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceUpdateNotSupportedException;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

/**
 * Service Instance Service implementation to manage service instances.
 * <p>
 * NOTE:
 * Creating a service does the following:
 * <p>
 * 1. Creates a new broker instance
 * 2. Saves the Service Instance info to the database.
 *
 * @author ipolyzos
 */
@Service
public class CloudKarafkaServiceInstanceService implements ServiceInstanceService {

    private BrokerConfig config;

    private CloudKarafkaAdminService cloudKarafkaAdminService;

    private CloudKarafkaServiceInstanceRepository repository;

    @Autowired
    public CloudKarafkaServiceInstanceService(final BrokerConfig config,
                                              final CloudKarafkaAdminService admin,
                                              final CloudKarafkaServiceInstanceRepository repository) {
        this.config = config;
        this.cloudKarafkaAdminService = admin;
        this.repository = repository;
    }

    @Override
    public CreateServiceInstanceResponse createServiceInstance(final CreateServiceInstanceRequest request) {
        ServiceInstance instance = repository.findOne(request.getServiceInstanceId());
        if (instance != null) {
            throw new ServiceInstanceExistsException(request.getServiceInstanceId(), request.getServiceDefinitionId());
        }

        instance = new ServiceInstance(request);

        if (cloudKarafkaAdminService.brokerInstanceExists(instance.getServiceInstanceId())) {
            // ensure the instance is empty
            cloudKarafkaAdminService.deleteBrokerInstance(instance.getServiceInstanceId());
        }

        //collect params
        final String iid = instance.getServiceInstanceId();
        final String iplan = instance.getPlanId().split("_")[0];

        final CloudKarafkaCreateInstanceResponse response = cloudKarafkaAdminService.createInstance(iid, iplan);
        if (response == null) {
            throw new ServiceBrokerException("Failed to create new broker instance: " + instance.getServiceInstanceId());
        }

        //fill object with required information
        instance.setCloudKarafkaId(response.getId());
        instance.setCloudKarafkaBrokers(response.getBrokers());
        instance.setCloudKarafkaCa(response.getCa());
        instance.setCloudKarafkaCert(response.getCert());
        instance.setCloudKarafkaPrivateKey(response.getPrivateKey());
        instance.setCloudKarafkaTopicPrefix(response.getTopicPrefix());
        instance.setCloudKarafkaMessage(response.getMessage());

        repository.save(instance);

        return new CreateServiceInstanceResponse();
    }

    @Override
    public GetLastServiceOperationResponse getLastOperation(final GetLastServiceOperationRequest request) {
        return new GetLastServiceOperationResponse().withOperationState(OperationState.SUCCEEDED);
    }

    public ServiceInstance getServiceInstance(String id) {
        return repository.findOne(id);
    }

    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(final DeleteServiceInstanceRequest request) throws CloudKarafkaServiceException {
        final String instanceId = request.getServiceInstanceId();
        final ServiceInstance instance = repository.findOne(instanceId);

        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        cloudKarafkaAdminService.deleteBrokerInstance(instance.getCloudKarafkaId());
        repository.delete(instanceId);

        return new DeleteServiceInstanceResponse();
    }

    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(final UpdateServiceInstanceRequest request) {
        final String instanceId = request.getServiceInstanceId();
        final ServiceInstance instance = repository.findOne(instanceId);

        if (instance == null) {
            throw new ServiceInstanceDoesNotExistException(instanceId);
        }

        if (request.equals(instance)) {
            throw new ServiceInstanceUpdateNotSupportedException("No changes in the change request");
        }

        // extract required values
        final String reqplan = request.getPlanId().split("_")[0];
        final String brkId = instance.getCloudKarafkaId();

        // change instance to a new plan
        cloudKarafkaAdminService.changeInstance(brkId, instance.getServiceInstanceId(), reqplan);
        instance.setPlanId(request.getPlanId());

        //replace old instance
        repository.delete(instanceId);
        repository.save(instance);

        return new UpdateServiceInstanceResponse();
    }
}
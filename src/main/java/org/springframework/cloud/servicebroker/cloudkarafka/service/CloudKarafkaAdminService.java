package org.springframework.cloud.servicebroker.cloudkarafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.cloudkarafka.config.BrokerConfig;
import org.springframework.cloud.servicebroker.cloudkarafka.exception.CloudKarafkaServiceException;
import org.springframework.cloud.servicebroker.cloudkarafka.model.CloudKarafkaCreateInstanceResponse;
import org.springframework.cloud.servicebroker.cloudkarafka.model.CloudKarafkaInstance;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;

/**
 * Utility class for manipulating CloudKarafka account
 *
 * @author ipolyzos
 */
@Service
public class CloudKarafkaAdminService {

    private Logger logger = LoggerFactory.getLogger(CloudKarafkaAdminService.class);

    /**
     * Service Broker Config
     */
    private BrokerConfig brokerConfig;

    /**
     * Jersey Rest Client
     */
    private Client client;

    @Autowired
    public CloudKarafkaAdminService(final Client restClient,
                                    final BrokerConfig brokerConfig) {
        this.client = restClient;
        this.brokerConfig = brokerConfig;
    }

    /**
     * Create a broker Instance
     *
     * @param brokerName
     * @return
     * @throws CloudKarafkaServiceException
     */
    public CloudKarafkaCreateInstanceResponse createInstance(final String brokerName,
                                                             final String plan) throws CloudKarafkaServiceException {
        // build input form
        final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("name", brokerName);
        form.add("plan", plan);
        form.add("region", brokerConfig.getCloudKarafkaRegion());

        //build post request
        final String target = String.format("%s/%s", brokerConfig.getCloudkarafkaApiUrl(), "instances");
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        return webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form), CloudKarafkaCreateInstanceResponse.class);
    }

    /**
     * Change a broker Instance
     *
     * @param brokerName
     * @throws CloudKarafkaServiceException
     */
    public void changeInstance(final String instanseId,
                               final String brokerName,
                               final String plan) throws CloudKarafkaServiceException {
        // build input form
        final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.add("name", brokerName);
        form.add("plan", plan);

        //build post request
        final String target = String.format("%s/%s/%s", brokerConfig.getCloudkarafkaApiUrl(), "instances",instanseId);
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        webTarget.request(MediaType.APPLICATION_JSON).put(Entity.form(form));
    }

    /**
     * Check if Broker Instance exists
     *
     * @param brokerName
     * @return
     * @throws CloudKarafkaServiceException
     */
    public boolean brokerInstanceExists(final String brokerName) throws CloudKarafkaServiceException {
        final String target = String.format("%s/%s", brokerConfig.getCloudkarafkaApiUrl(), "instances");
        final WebTarget webTarget = client.target(target);

        // call create broker instances API
        final CloudKarafkaInstance[] response = webTarget.request(MediaType.APPLICATION_JSON)
                .get(CloudKarafkaInstance[].class);

        // filter brokers list to find the one with the given id
        final CloudKarafkaInstance inst = Arrays.stream(response).filter(x -> x.getId().equals(brokerName)).findAny().orElse(null);

        return inst != null;
    }


    /**
     * Delete a broker instance
     *
     * @param brokerInstanceId
     * @throws CloudKarafkaServiceException
     */
    public String deleteBrokerInstance(final String brokerInstanceId) throws CloudKarafkaServiceException {
        final String target = String.format("%s/%s/%s", brokerConfig.getCloudkarafkaApiUrl(), "instances", brokerInstanceId);
        final WebTarget webTarget = client.target(target);

        // call delete broker instance API
        return webTarget.request().delete(String.class);
    }

}

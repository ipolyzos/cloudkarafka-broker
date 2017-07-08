package org.springframework.cloud.servicebroker.cloudkarafka.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceRequest;

/**
 * An instance of a Service Definition.
 *
 * @author ipolyzos
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServiceInstance {

    @JsonSerialize
    @JsonProperty("service_instance_id")
    private String id;

    @JsonSerialize
    @JsonProperty("service_id")
    private String serviceDefinitionId;

    @JsonSerialize
    @JsonProperty("plan_id")
    private String planId;

    @JsonSerialize
    @JsonProperty("organization_guid")
    private String organizationGuid;

    @JsonSerialize
    @JsonProperty("space_guid")
    private String spaceGuid;

    @JsonSerialize
    @JsonProperty("dashboard_url")
    private String dashboardUrl;

    @JsonSerialize
    @JsonProperty("cloudKarafka_id")
    private String cloudKarafkaId;

    @JsonSerialize
    @JsonProperty("cloudKarafka_ca")
    private String cloudKarafkaCa;

    @JsonSerialize
    @JsonProperty("cloudKarafka_cert")
    private String cloudKarafkaCert;

    @JsonSerialize
    @JsonProperty("cloudKarafka_private_key")
    private String cloudKarafkaPrivateKey;

    @JsonSerialize
    @JsonProperty("cloudKarafka_topic_prefix")
    private String cloudKarafkaTopicPrefix;

    @JsonSerialize
    @JsonProperty("cloudKarafka_brokers")
    private String cloudKarafkaBrokers;

    @JsonSerialize
    @JsonProperty("cloudKarafka_message")
    private String cloudKarafkaMessage;

    @SuppressWarnings("unused")
    private ServiceInstance() {
    }

    public ServiceInstance(String serviceInstanceId, String serviceDefinitionId, String planId,
                           String organizationGuid, String spaceGuid, String dashboardUrl) {
        this.id = serviceInstanceId;
        this.serviceDefinitionId = serviceDefinitionId;
        this.planId = planId;
        this.organizationGuid = organizationGuid;
        this.spaceGuid = spaceGuid;
        this.dashboardUrl = dashboardUrl;
    }

    /**
     * Create a ServiceInstance from a create request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(CreateServiceInstanceRequest request) {
        this.serviceDefinitionId = request.getServiceDefinitionId();
        this.planId = request.getPlanId();
        this.organizationGuid = request.getOrganizationGuid();
        this.spaceGuid = request.getSpaceGuid();
        this.id = request.getServiceInstanceId();
    }

    /**
     * Create a ServiceInstance from a delete request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(DeleteServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
        this.serviceDefinitionId = request.getServiceDefinitionId();
    }

    /**
     * Create a service instance from an update request. If fields
     * are not present in the request they will remain null in the
     * ServiceInstance.
     *
     * @param request containing details of ServiceInstance
     */
    public ServiceInstance(UpdateServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
    }

    public String getServiceInstanceId() {
        return id;
    }

    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public String getCloudKarafkaCa() {
        return cloudKarafkaCa;
    }

    public void setCloudKarafkaCa(String cloudKarafkaCa) {
        this.cloudKarafkaCa = cloudKarafkaCa;
    }

    public String getCloudKarafkaId() {
        return cloudKarafkaId;
    }

    public void setCloudKarafkaId(String cloudKarafkaId) {
        this.cloudKarafkaId = cloudKarafkaId;
    }

    public String getCloudKarafkaCert() {
        return cloudKarafkaCert;
    }

    public void setCloudKarafkaCert(String cloudKarafkaCert) {
        this.cloudKarafkaCert = cloudKarafkaCert;
    }

    public String getCloudKarafkaPrivateKey() {
        return cloudKarafkaPrivateKey;
    }

    public void setCloudKarafkaPrivateKey(String cloudKarafkaPrivateKey) {
        this.cloudKarafkaPrivateKey = cloudKarafkaPrivateKey;
    }

    public String getCloudKarafkaTopicPrefix() {
        return cloudKarafkaTopicPrefix;
    }

    public void setCloudKarafkaTopicPrefix(String cloudKarafkaTopicPrefix) {
        this.cloudKarafkaTopicPrefix = cloudKarafkaTopicPrefix;
    }

    public String getCloudKarafkaBrokers() {
        return cloudKarafkaBrokers;
    }

    public void setCloudKarafkaBrokers(String cloudKarafkaBrokers) {
        this.cloudKarafkaBrokers = cloudKarafkaBrokers;
    }

    public String getCloudKarafkaMessage() {
        return cloudKarafkaMessage;
    }

    public void setCloudKarafkaMessage(String cloudKarafkaMessage) {
        this.cloudKarafkaMessage = cloudKarafkaMessage;
    }

    public ServiceInstance and() {
        return this;
    }

    public ServiceInstance withDashboardUrl(String dashboardUrl) {
        this.dashboardUrl = dashboardUrl;
        return this;
    }
}

package org.springframework.cloud.servicebroker.cloudkarafka.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * An instance of a CloudKarafka Create Instance Response Definition
 *
 * @author ipolyzos
 */
public class CloudKarafkaCreateInstanceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("PRIVATE_KEY")
    private String privateKey;

    @JsonProperty("CERT")
    private String cert;

    @JsonProperty("CA")
    private String ca;

    @JsonProperty("TOPIC_PREFIX")
    private String topicPrefix;

    @JsonProperty("BROKERS")
    private String brokers;

    @JsonProperty("message")
    private String message;

    public CloudKarafkaCreateInstanceResponse() {
        super();
    }

    public CloudKarafkaCreateInstanceResponse(final String id,
                                              final String privateKey,
                                              final String cert,
                                              final String ca,
                                              final String topicPrefix,
                                              final String brokers,
                                              final String message) {
        this.id = id;
        this.privateKey = privateKey;
        this.cert = cert;
        this.ca = ca;
        this.topicPrefix = topicPrefix;
        this.brokers = brokers;
        this.message = message;
    }

    public CloudKarafkaCreateInstanceResponse(final String privateKey,
                                              final String cert,
                                              final String ca,
                                              final String topicPrefix,
                                              final String brokers,
                                              final String message) {
        this.privateKey = privateKey;
        this.cert = cert;
        this.ca = ca;
        this.topicPrefix = topicPrefix;
        this.brokers = brokers;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(final String privateKey) {
        this.privateKey = privateKey;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(final String cert) {
        this.cert = cert;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(final String ca) {
        this.ca = ca;
    }

    public String getTopicPrefix() {
        return topicPrefix;
    }

    public void setTopicPrefix(final String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(final String brokers) {
        this.brokers = brokers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

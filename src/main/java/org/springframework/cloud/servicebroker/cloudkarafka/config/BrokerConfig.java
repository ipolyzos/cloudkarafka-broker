package org.springframework.cloud.servicebroker.cloudkarafka.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@EnableMongoRepositories(basePackages = "org.springframework.cloud.servicebroker.cloudkarafka.repository")
public class BrokerConfig {

    @Value("${CLOUDKARAFKA_API_URL:https://customer.cloudkarafka.com/api}")
    private String cloudkarafkaApiUrl;

    @Value("${CLOUDKARAFKA_API_KEY}")
    private String cloudKarafkaApiKey;

    @Value("${CLOUDKARAFKA_REGION:amazon-web-services::eu-west-1}")
    private String cloudKarafkaRegion;

    /**
     * Build a Jersey http client instance
     *
     * @return Client
     */
    @Bean
    public Client restClient() {
        final ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        final HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature.basic(cloudKarafkaApiKey, "");

        final Client client = ClientBuilder.newClient(cc);
        client.register(httpAuthenticationFeature);

        return client;
    }

    public String getCloudkarafkaApiUrl() {
        return cloudkarafkaApiUrl;
    }

    public void setCloudkarafkaApiUrl(String cloudkarafkaApiUrl) {
        this.cloudkarafkaApiUrl = cloudkarafkaApiUrl;
    }

    public String getCloudKarafkaApiKey() {
        return cloudKarafkaApiKey;
    }

    public void setCloudKarafkaApiKey(String cloudKarafkaApiKey) {
        this.cloudKarafkaApiKey = cloudKarafkaApiKey;
    }

    public String getCloudKarafkaRegion() {
        return cloudKarafkaRegion;
    }

    public void setCloudKarafkaRegion(String cloudKarafkaRegion) {
        this.cloudKarafkaRegion = cloudKarafkaRegion;
    }
}

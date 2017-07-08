package org.springframework.cloud.servicebroker.cloudkarafka.model.proxy;

import org.springframework.cloud.servicebroker.model.DashboardClient;
import org.springframework.stereotype.Component;

@Component
public class DashboardClientProxy {
    private String id;
    private String secret;
    private String redirectUrl;

    public DashboardClientProxy() {
        super();
    }

    public DashboardClientProxy(String id, String secret, String redirectUrl) {
        super();
        this.id = id;
        this.secret = secret;
        this.redirectUrl = redirectUrl;
    }

    public DashboardClient unproxy() {
        return new DashboardClient(id, secret, redirectUrl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
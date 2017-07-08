package org.springframework.cloud.servicebroker.cloudkarafka.model;

import java.io.Serializable;

/**
 * An instance of a Karaf Instance Definition
 *
 * @author ipolyzos
 */
public class CloudKarafkaInstance implements Serializable {

    private static final long serialVersionUID = 612743187295887480L;

    private String id;
    private String name;
    private String plan;
    private String region;

    public CloudKarafkaInstance() {
        super();
    }

    public CloudKarafkaInstance(final String name,
                                final String plan, String region) {
        this.name = name;
        this.plan = plan;
        this.region = region;
    }

    public CloudKarafkaInstance(final String id,
                                final String name,
                                final String plan,
                                final String region) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(final String plan) {
        this.plan = plan;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }
}

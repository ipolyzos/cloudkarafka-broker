package org.springframework.cloud.servicebroker.cloudkarafka.exception;

import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;

/**
 * Exception thrown when issues with the underlying CloudKarafka service occur.
 *
 * @author ipolyzos
 */
public class CloudKarafkaServiceException extends ServiceBrokerException {

    private static final long serialVersionUID = 2960225362135115762L;

    public CloudKarafkaServiceException(String message) {
        super(message);
    }

}

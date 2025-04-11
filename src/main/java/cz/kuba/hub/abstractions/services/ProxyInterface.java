package cz.kuba.hub.abstractions.services;

import cz.kuba.hub.models.ServiceDTO;

/**
 * Interface defying how proxies should work for different {@link cz.kuba.hub.enums.ServiceType}.
 */
public interface ProxyInterface {

    /**
     * Send incoming request to the service and returns its response.
     * @param service Called service
     * @param headers Headers of request
     * @param url
     * @param body
     */
    String send(ServiceDTO service,
              String headers,
              String url,
              String body);
}

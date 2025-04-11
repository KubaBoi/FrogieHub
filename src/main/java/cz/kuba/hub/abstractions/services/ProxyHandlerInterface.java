package cz.kuba.hub.abstractions.services;

import cz.kuba.hub.enums.ServiceType;
import cz.kuba.hub.models.RequestDTO;

/**
 * Interface defying how proxies should work for different {@link cz.kuba.hub.enums.ServiceType}.
 */
public interface ProxyHandlerInterface {

    /**
     * Type of requests which implementation handles.
     */
    ServiceType getServiceType();

    /**
     * Send incoming request to the service and returns its response.
     *
     * @param request
     */
    byte[] forward(RequestDTO request);
}

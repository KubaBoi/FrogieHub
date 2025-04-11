package cz.kuba.hub.services;

import cz.kuba.hub.abstractions.services.ProxyHandlerInterface;
import cz.kuba.hub.enums.ServiceType;
import cz.kuba.hub.models.RequestDTO;

import java.nio.charset.StandardCharsets;

/**
 * Proxy handler for HTTP services.
 */
public class HttpProxyHandler implements ProxyHandlerInterface {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.HTTP;
    }

    @Override
    public byte[] forward(RequestDTO request) {
        return request.request().getRequestURI().getBytes(StandardCharsets.UTF_8);
    }
}

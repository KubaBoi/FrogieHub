package cz.kuba.hub.services;

import cz.kuba.hub.abstractions.services.ProxyInterface;
import cz.kuba.hub.models.ServiceDTO;
import org.springframework.stereotype.Service;

/**
 * Proxy service for HTTP services.
 */
@Service
public class HttpProxyService implements ProxyInterface {

    @Override
    public String send(ServiceDTO service, String url, String headers, String body) {
        return url + " " + headers + " " + body;
    }
}

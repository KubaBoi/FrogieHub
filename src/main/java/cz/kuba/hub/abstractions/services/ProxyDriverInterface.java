package cz.kuba.hub.abstractions.services;

import cz.kuba.hub.enums.DriverType;
import cz.kuba.hub.models.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Interface defying how proxies should work for different {@link DriverType}.
 */
public interface ProxyDriverInterface {

    /**
     * Type of requests which implementation handles.
     */
    DriverType getServiceType();

    /**
     * Send incoming request to the service and returns its response.
     *
     * @param service
     * @param request
     */
    ResponseEntity<byte[]> forward(Service service, HttpServletRequest request) throws IOException;
}

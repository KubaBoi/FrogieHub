package cz.kuba.hub.services;

import cz.kuba.hub.abstractions.services.ProxyHandlerInterface;
import cz.kuba.hub.enums.ServiceType;
import cz.kuba.hub.models.ServiceDTO;
import jakarta.annotation.Nullable;
import jakarta.el.MethodNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Service
public class SelectorService {

    private final List<ServiceDTO> services;
    private final List<ProxyHandlerInterface> proxyHandlers;

    public SelectorService() {
        services = List.of(
                new ServiceDTO("wiokno", "Wiokno", "aa", 7111, ServiceType.HTTP)
        );

        proxyHandlers = List.of(
                new HttpProxyHandler()
        );
    }

    public ServiceDTO findService(String serviceId, @Nullable String referer) throws ServiceNotFoundException {
        if (serviceId != null) {
            for (ServiceDTO service : services) {
                if (serviceId.equals(service.serviceId()))
                    return service;
            }
        }

        // service from url was not found
        // trying to use referer (for scripts, images, favicon,...)
        if (referer != null) {
            return findService(findServiceId(referer), null);
        }
        throw new ServiceNotFoundException("Service not found");
    }

    public ProxyHandlerInterface findProxyHandler(ServiceType serviceType) {
        for (ProxyHandlerInterface proxyHandler : proxyHandlers) {
            if (proxyHandler.getServiceType().equals(serviceType))
                return proxyHandler;
        }
        throw new MethodNotFoundException("Proxy handler not found");
    }

    private String findServiceId(String url) {
        var parts = url.split("/");
        if (parts.length >= 4)
            return parts[3];
        return null;
    }
}

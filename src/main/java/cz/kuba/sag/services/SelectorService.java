package cz.kuba.sag.services;

import cz.kuba.sag.abstractions.services.ProxyDriverInterface;
import cz.kuba.sag.enums.DriverType;
import cz.kuba.sag.models.Service;
import cz.kuba.sag.repositories.ServiceRepository;
import jakarta.el.MethodNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ServiceNotFoundException;
import java.util.List;

@org.springframework.stereotype.Service
public class SelectorService {

    private static final Logger log = LoggerFactory.getLogger(SelectorService.class);
    private final ServiceRepository serviceRepository;

    private final List<Service> services;
    private final List<ProxyDriverInterface> proxyDrivers;

    public SelectorService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;

        services = List.of(
                new Service(0,
                        "wiokno",
                        "Wiokno",
                        null,
                        7970,
                        DriverType.HTTP)
        );

        proxyDrivers = List.of(
                new HttpProxyDriver()
        );
    }

    public Service findService(String serviceId) throws ServiceNotFoundException {
        if (serviceId != null) {
            for (Service service : services) {
                if (serviceId.equals(service.getServiceId()))
                    return service;
            }
        }
        log.error("No service found with id {}", serviceId);
        throw new ServiceNotFoundException("Service not found");
    }

    public ProxyDriverInterface findProxyDriver(DriverType serviceType) {
        for (ProxyDriverInterface proxyDriver : proxyDrivers) {
            if (proxyDriver.getServiceType().equals(serviceType))
                return proxyDriver;
        }
        log.error("No proxy driver found for service type {}", serviceType);
        throw new MethodNotFoundException("Proxy driver not found");
    }
}

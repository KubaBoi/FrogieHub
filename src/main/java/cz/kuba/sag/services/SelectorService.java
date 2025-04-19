package cz.kuba.sag.services;

import cz.kuba.sag.abstractions.services.ProxyDriverInterface;
import cz.kuba.sag.enums.DriverType;
import cz.kuba.sag.models.SasService;
import cz.kuba.sag.repositories.ServiceRepository;
import jakarta.el.MethodNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Service
public class SelectorService {

    private static final Logger log = LoggerFactory.getLogger(SelectorService.class);
    private final ServiceRepository serviceRepository;

    private final List<ProxyDriverInterface> proxyDrivers;

    public SelectorService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;

        proxyDrivers = List.of(
                new HttpProxyDriver()
        );
    }

    public SasService findService(String prefix) throws ServiceNotFoundException {
        SasService service = serviceRepository.findByPrefix(prefix);
        if (service == null) {
            log.error("No service found with id {}", prefix);
            throw new ServiceNotFoundException("Service not found");
        }
        return service;
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

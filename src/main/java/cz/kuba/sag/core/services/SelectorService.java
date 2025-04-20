package cz.kuba.sag.core.services;

import cz.kuba.sag.abstractions.ProxyDriverInterface;
import cz.kuba.sag.drivers.HttpProxyDriver;
import cz.kuba.sag.data.enums.DriverType;
import cz.kuba.sag.data.models.entities.SasService;
import cz.kuba.sag.data.repositories.ServiceRepository;
import jakarta.el.MethodNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Slf4j
@Service
public class SelectorService {

    private final ServiceRepository serviceRepository;

    private final List<ProxyDriverInterface> proxyDrivers;

    public SelectorService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;

        proxyDrivers = List.of(new HttpProxyDriver());
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
            if (proxyDriver.getServiceType().equals(serviceType)) return proxyDriver;
        }
        log.error("No proxy driver found for service type {}", serviceType);
        throw new MethodNotFoundException("Proxy driver not found");
    }
}

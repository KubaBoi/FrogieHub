package cz.kuba.hub.controllers;

import cz.kuba.hub.services.SelectorService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/{serviceId}/**")
public class ProxyController {

    private static final Logger log = LoggerFactory.getLogger(ProxyController.class);
    private final SelectorService selectorService;

    @Autowired
    public ProxyController(SelectorService selectorService) {
        this.selectorService = selectorService;
    }

    @ResponseBody
    @RequestMapping(
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.DELETE,
                    RequestMethod.PATCH,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.TRACE
            }
    )
    public ResponseEntity<byte[]> handler(@PathVariable String serviceId,
                                  HttpServletRequest request) throws ServiceNotFoundException, IOException {
        log.info("Forwarding {} - '{}' request",
                request.getMethod(),
                request.getRequestURI());

        var service = selectorService.findService(serviceId);
        var driver = selectorService.findProxyDriver(service.getDriverType());

        return driver.forward(service, request);
    }
}

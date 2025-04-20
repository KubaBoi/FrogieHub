package cz.kuba.sag.controllers;

import cz.kuba.sag.services.SelectorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/{servicePrefix}/**")
public class ProxyController {

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
    public ResponseEntity<byte[]> handler(@PathVariable String servicePrefix,
                                  HttpServletRequest request) throws ServiceNotFoundException, IOException {
        log.info("Forwarding {} - '{}' request",
                request.getMethod(),
                request.getRequestURI());

        var service = selectorService.findService(servicePrefix);
        var driver = selectorService.findProxyDriver(service.getDriverType());

        return driver.forward(service, request);
    }
}

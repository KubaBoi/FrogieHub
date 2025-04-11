package cz.kuba.hub.controllers;

import cz.kuba.hub.models.RequestDTO;
import cz.kuba.hub.services.SelectorService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;

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
    @GetMapping
    @PostMapping
    public byte[] getHandler(@PathVariable String serviceId,
                             HttpServletRequest request) throws ServiceNotFoundException {
        log.info("Forwarding {} - '{}' request",
                request.getMethod(),
                request.getRequestURI());

        var referer = request.getHeader("Referer");
        var service = selectorService.findService(serviceId, referer);
        var handler = selectorService.findProxyHandler(service.type());

        var req = new RequestDTO(service, request);

        return handler.forward(req);
    }
}

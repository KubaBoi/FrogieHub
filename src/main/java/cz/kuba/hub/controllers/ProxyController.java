package cz.kuba.hub.controllers;

import cz.kuba.hub.services.HttpProxyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/**")
public class ProxyController {

    private final HttpProxyService httpProxyService;

    @Autowired
    public ProxyController(HttpProxyService httpProxyService) {
        this.httpProxyService = httpProxyService;
    }

    @ResponseBody
    @GetMapping
    public String getTestData(HttpServletRequest request) {
        var uri = request.getRequestURI();
        return this.httpProxyService.send(null, uri, "bb", "cc");
    }
}

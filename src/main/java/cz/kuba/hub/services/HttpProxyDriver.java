package cz.kuba.hub.services;

import cz.kuba.hub.abstractions.services.ProxyDriverInterface;
import cz.kuba.hub.enums.DriverType;
import cz.kuba.hub.models.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Proxy handler for HTTP services.
 */
public class HttpProxyDriver implements ProxyDriverInterface {

    private static final Logger log = LoggerFactory.getLogger(HttpProxyDriver.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public DriverType getServiceType() {
        return DriverType.HTTP;
    }

    @Override
    public ResponseEntity<byte[]> forward(Service service, HttpServletRequest request) throws IOException {
        var url = createUrl(service, request.getRequestURI(), request.getQueryString());
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(name ->
                headers.put(name, Collections.list(request.getHeaders(name)))
        );

        byte[] body = request.getInputStream().readAllBytes();

        log.info("Sending request to {}", url);
        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                url,
                method,
                entity,
                byte[].class);

        log.info("Received response from {}. Status: {}", url, responseEntity.getStatusCode());
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(responseEntity.getBody());
    }

    private String createUrl(Service service, String path, String query) throws UnsupportedEncodingException {
        path = path.replace(service.getServiceId(), "");

        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:")
                .append(service.getPort())
                .append(URLDecoder.decode(path, StandardCharsets.UTF_8));

        if (query != null) {
            builder.append("?")
                    .append(URLDecoder.decode(query, StandardCharsets.UTF_8));
        }

        return builder.toString();
    }
}

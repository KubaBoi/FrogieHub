package cz.kuba.sag.drivers;

import cz.kuba.sag.abstractions.ProxyDriverInterface;
import cz.kuba.sag.data.enums.DriverType;
import cz.kuba.sag.data.models.entities.SasService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HttpProxyDriver implements ProxyDriverInterface {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public DriverType getServiceType() {
        return DriverType.HTTP;
    }

    @Override
    public ResponseEntity<byte[]> forward(SasService service, HttpServletRequest request) throws IOException {
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

    private String createUrl(SasService service, String path, String query) throws UnsupportedEncodingException {
        path = path.replace(service.getPrefix(), "");

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

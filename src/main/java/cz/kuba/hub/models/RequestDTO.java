package cz.kuba.hub.models;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Object representing incoming request.
 *
 * @param service Called service
 * @param request Request data
 */
public record RequestDTO(Service service,
                         HttpServletRequest request) {
}

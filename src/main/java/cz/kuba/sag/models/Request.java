package cz.kuba.sag.models;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Object representing incoming request.
 *
 * @param service Called service
 * @param request Request data
 */
public record Request(Service service,
                      HttpServletRequest request) {
}

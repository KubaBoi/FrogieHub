package cz.kuba.sag.data.models;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Object representing incoming request.
 *
 * @param service Called service
 * @param request Request data
 */
public record Request(SasService service,
                      HttpServletRequest request) {
}

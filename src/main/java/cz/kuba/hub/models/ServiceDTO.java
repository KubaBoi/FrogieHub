package cz.kuba.hub.models;

import cz.kuba.hub.enums.ServiceType;

/**
 * Object representing service to reroute requests.
 *
 * @param id          Database PK
 * @param name        Display name
 * @param description Short description
 * @param path        Path of reroute
 * @param port        Internal port
 * @param type        Type of service's interface
 */
public record ServiceDTO(String id,
                         String name,
                         String description,
                         String path,
                         Integer port,
                         ServiceType type) {
}

package cz.kuba.hub.models;

import cz.kuba.hub.enums.ServiceType;

/**
 * Object representing service to reroute requests.
 *
 * @param serviceId   Unique id od service in the start of url and database PK
 * @param name        Display name
 * @param description Short description
 * @param port        Internal port
 * @param type        Type of service's interface
 */
public record ServiceDTO(String serviceId,
                         String name,
                         String description,
                         Integer port,
                         ServiceType type) {
}

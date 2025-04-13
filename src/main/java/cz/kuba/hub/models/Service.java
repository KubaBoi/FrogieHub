package cz.kuba.hub.models;

import cz.kuba.hub.enums.DriverType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representing service to reroute requests.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    /**
     * Unique id od service in the start of url
     */
    @Id
    private String serviceId;

    /**
     * Display name
     */
    private String name;

    /**
     * Short description
     */
    private String description;

    /**
     * Internal port
     */
    private Integer port;

    /**
     * Type of service's interface
     */
    private DriverType driverType;
}

package cz.kuba.sag.models;

import cz.kuba.sag.enums.DriverType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object representing service to reroute requests.
 */
@Entity
@Table(name = "services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    private Integer id;

    /**
     * Unique id od service in the start of url
     */
    @Column(nullable = false, unique = true)
    private String serviceId;

    /**
     * Display name
     */
    @Column(nullable = false)
    private String name;

    /**
     * Short description
     */
    @Column
    private String description;

    /**
     * Internal port
     */
    @Column(nullable = false, unique = true)
    private Integer port;

    /**
     * Type of service's interface
     */
    @Column(nullable = false)
    private DriverType driverType;
}

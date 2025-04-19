package cz.kuba.sag.models;

import cz.kuba.sag.enums.DriverType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Unique id od service in the start of url
     */
    @Column(nullable = false, unique = true)
    private String prefix;

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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverType driverType;
}

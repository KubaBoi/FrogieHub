package cz.kuba.sag.data.models.entities;

import cz.kuba.sag.data.enums.DriverType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Object representing SAS service to reroute requests.
 */
@Entity
@Table(name = "services")
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SasService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

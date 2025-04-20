package cz.kuba.sag.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kuba.sag.data.enums.DriverType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    @JsonProperty
    private UUID id;
    @JsonProperty
    private String prefix;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer port;
    @JsonProperty
    private DriverType driverType;
}

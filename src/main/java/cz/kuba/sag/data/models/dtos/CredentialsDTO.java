package cz.kuba.sag.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CredentialsDTO {

    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private String password;
}

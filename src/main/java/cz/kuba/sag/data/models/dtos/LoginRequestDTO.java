package cz.kuba.sag.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDTO {

    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private String password;
}

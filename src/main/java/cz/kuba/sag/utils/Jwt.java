package cz.kuba.sag.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {

    private Header header;
    private Payload payload;

    public static Jwt parse(String token) {
        return new Jwt();
    }

    private static String joinHeaderAndPayload(String headerJson, String payloadJson) {
        return String.format("%s.%s",
                Arrays.toString(Base64.getDecoder().decode(headerJson)),
                Arrays.toString(Base64.getDecoder().decode(payloadJson)));
    }

    public String build(String key) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        ObjectMapper mapper = new ObjectMapper();

        String headerJson = mapper.writeValueAsString(this.header);
        String payloadJson = mapper.writeValueAsString(this.payload);

        String joined = joinHeaderAndPayload(headerJson, payloadJson);

        String signature = CryptoUtils.hmac(
                "HmacSHA256",
                joined,
                "123456789");

        return joined + "." + signature;
    }

    @Getter
    @Setter
    public static class Header {
        @JsonProperty("alg")
        private String algorithm = "HS256";
        @JsonProperty("typ")
        private String type = "typ";
    }

    @Getter
    @Setter
    public static class Payload {
        @JsonProperty("iss")
        private String issuer;
        @JsonProperty("sub")
        private String subject;
        @JsonProperty("aud")
        private String audience;
        @JsonProperty("exp")
        private Date expiration;
    }
}

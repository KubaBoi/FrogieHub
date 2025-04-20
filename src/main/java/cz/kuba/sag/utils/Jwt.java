package cz.kuba.sag.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.kuba.sag.core.exceptions.InvalidTokenFormatException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class Jwt {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private Header header;
    private Payload payload;

    /**
     * Parse {@link Jwt} no matter of validity
     * @param token JWT token
     * @return {@link Jwt}
     */
    public static Jwt parse(String token) throws IOException {
        var parts = splitToken(token);
        String headerJson = CryptoUtils.fromBase64Url(parts[0]);
        String payloadJson = CryptoUtils.fromBase64Url(parts[1]);

        return new Jwt()
                .header(mapper.reader().readValue(headerJson, Header.class))
                .payload(mapper.reader().readValue(payloadJson, Payload.class));
    }

    public static Boolean validate(String token, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        var parts = splitToken(token);

        byte[] sigBytes = CryptoUtils.hmac(
                "HmacSHA256",
                parts[0] + "." + parts[1],
                key);
        String sigB64 = CryptoUtils.toBase64Url(sigBytes);
        return sigB64.equals(parts[2]);
    }

    public String build(String key) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        if (Objects.isNull(header)) {
            header = new Header();
        }

        String headerJson = mapper.writeValueAsString(this.header);
        String payloadJson = mapper.writeValueAsString(this.payload);

        String joined = String.format("%s.%s",
                CryptoUtils.toBase64Url(headerJson.getBytes()),
                CryptoUtils.toBase64Url(payloadJson.getBytes()));

        byte[] sigBytes = CryptoUtils.hmac(
                "HmacSHA256",
                joined,
                key);
        String sigB64 = CryptoUtils.toBase64Url(sigBytes);

        return joined + "." + sigB64;
    }

    private static String[] splitToken(String token) {
        var parts = token.split("\\.");
        if (parts.length != 3) {
            log.error("Invalid token");
            throw new InvalidTokenFormatException("Invalid token");
        }
        return parts;
    }

    @Getter
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Header {
        @JsonProperty("alg")
        private String algorithm = "HS256";
        @JsonProperty("typ")
        private String type = "JWT";
    }

    @Getter
    @Setter
    @Accessors(chain = true, fluent = true)
    public static class Payload {
        @JsonProperty("iss")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String issuer;
        @JsonProperty("sub")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String subject;
        @JsonProperty("aud")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String audience;
        @JsonProperty("exp")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Instant expiration;
        @JsonProperty("iat")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Instant issued;
    }
}

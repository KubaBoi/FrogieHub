package cz.kuba.sag.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class CryptoTests {

    private final String KEY = "123456789123456789123456789123456789";
    private final String KEY2 = "123456789123456789123456789123456788";

    private final String EXPECTED_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJleHAiOiIxOTcwLTAxLTAxVDAwOjAwOjAwWiJ9.djW6Nzu9c1Kxgg7cUKxXnxblQiRWBX7qABkwK2AwHyI";

    @Test
    public void generateTokenTest() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Instant now = Instant.EPOCH;

        String jwt = new Jwt()
                .payload(new Jwt.Payload()
                        .issuer("issuer")
                        .audience("audience")
                        .subject("subject")
                        .expiration(now)
                )
                .build(KEY);

        Assertions.assertEquals(EXPECTED_JWT, jwt);
    }

    @ParameterizedTest
    @CsvSource(value = {
            KEY + ":true",
            KEY2 + ":false"
    }, delimiter = ':')
    public void verifyTokenTest(String key, Boolean expected) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Boolean result = Jwt.validate(EXPECTED_JWT, key);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void parseTokenTest() throws IOException {
        Jwt result = Jwt.parse(EXPECTED_JWT);

        Assertions.assertEquals("HS256", result.header().algorithm());
        Assertions.assertEquals("JWT", result.header().type());
        Assertions.assertEquals("issuer", result.payload().issuer());
        Assertions.assertEquals("audience", result.payload().audience());
        Assertions.assertEquals("subject", result.payload().subject());
        Assertions.assertEquals(Instant.EPOCH, result.payload().expiration());
    }
}

package cz.kuba.sag.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CryptoUtils {

    private static final String KEY = "123456789";
    private static final String HEADER = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";

    public static String hmac(String algorithm, String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(signingKey);
        return Arrays.toString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
}

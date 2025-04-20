package cz.kuba.sag.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {

    public static byte[] hmac(String algorithm, String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    //<editor-fold desc="Base64">

    public static String toBase64Url(byte[] data) {
        return toBase64Url(data, false);
    }

    public static String toBase64Url(byte[] data, boolean withPadding) {
        var encoder = Base64.getEncoder();
        if (!withPadding) {
            encoder = encoder.withoutPadding();
        }
        return encoder.encodeToString(data);
    }

    public static String fromBase64Url(String base64Url) {
        var bytes = Base64.getDecoder().decode(base64Url);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    //</editor-fold>
}

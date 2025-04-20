package cz.kuba.sag.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {

    //<editor-fold desc="Header">
    private String algorithm;

    //</editor-fold>

    //<editor-fold desc="Payload">

    private String issuer;
    private Date expiration;
    private String subject;
    private String audience;

    //</editor-fold>

    public static String build(String key) {
        return "";
    }

    public static Jwt parse(String token) {
        return new Jwt();
    }
}

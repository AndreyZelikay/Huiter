package bel.huiter.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class JWT {

    public static String createJTW (String body) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(SecurityConstants.SECRET.getBytes(),signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(SecurityConstants.ISSUER)
                .setSubject(body)
                .signWith(signatureAlgorithm, signingKey);
        return jwtBuilder.compact();
    }

    public static Claims decodeJWT(String token) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(SecurityConstants.SECRET.getBytes(),signatureAlgorithm.getJcaName());
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }
}

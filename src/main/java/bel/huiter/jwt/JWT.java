package bel.huiter.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

public class JWT {

    public static String createJTW (String body) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long currentTimeInMillis = System.currentTimeMillis();
        long expirationTimeInMillis = currentTimeInMillis + SecurityConstants.LiveTimeInMillis;
        Date expirationDate = new Date(expirationTimeInMillis);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(SecurityConstants.ISSUER)
                .setSubject(body)
                .signWith(signatureAlgorithm, DigestUtils.md5(SecurityConstants.SECRET))
                .setExpiration(expirationDate);
        return jwtBuilder.compact();
    }

    public static Claims decodeJWT(String token) {
        return Jwts.parser().setSigningKey(DigestUtils.md5(SecurityConstants.SECRET)).parseClaimsJws(token).getBody();
    }
}

package bel.huiter.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;

public class JWT {

    public static String createJTW (String body) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(SecurityConstants.ISSUER)
                .setSubject(body)
                .signWith(signatureAlgorithm, DigestUtils.md5(SecurityConstants.SECRET));
        return jwtBuilder.compact();
    }

    public static Claims decodeJWT(String token) {
        return Jwts.parser().setSigningKey(DigestUtils.md5(SecurityConstants.SECRET)).parseClaimsJws(token).getBody();
    }
}

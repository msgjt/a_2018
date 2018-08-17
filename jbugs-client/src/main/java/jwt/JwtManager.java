package jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtManager {

    private static JwtManager instance;

    public static JwtManager getInstance() {
        if (instance == null) {
            instance = new JwtManager();
        }
        return instance;
    }

    private static final String CLAIM_ROLE = "role";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);


    public String createToken(String user) {

        String jws = Jwts.builder().setSubject(user).claim("loggedInAs", "user").signWith(SECRET_KEY).compact();
        return jws;
    }

    public Jws<Claims> parseToken(final String token) {
        try {
            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return parsedToken;
        } catch (JwtException ex) {
            System.out.println("jwt invalid");
            return null;

        }
    }
}


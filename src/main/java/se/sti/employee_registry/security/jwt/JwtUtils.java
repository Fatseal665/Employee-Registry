package se.sti.employee_registry.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.sti.employee_registry.user.CustomUser;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private final SecretKey key;
    private final int jwtExpirationInMs = (int) TimeUnit.MINUTES.toMillis(10);

    public JwtUtils(@Value("${BASE64_ENCODED_SECRET_KEY}") String base64EncodedSecretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedSecretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(CustomUser customUser) {
        log.debug("Generate JWT token for {} with roles: {}", customUser.getEmail(),customUser.getRoles());

        List<String> roles = customUser.getRoles().stream().map(
                userRole -> userRole.getRoleName()
        ).toList();

        String token = Jwts.builder()
                .subject(customUser.getEmail())
                .claim("authorities", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .signWith(key)
                .compact();

        log.debug("JWT generatio successfully for user: {}", customUser.getEmail());
        return token;

    }

    public String getUsernameFromJwtToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseEncryptedClaims(token)
                    .getPayload();
            String username = claims.getSubject();
            log.debug("Extract username '{}' from token", username);
            return username;
        } catch (Exception e) {
            log.error("Failed to extract username from tocken: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseClaimsJws(authToken);

            log.debug("Jwt token validated");
            return true;
        } catch (Exception e) {
            log.error("Jwt token validation failed: {}", e.getMessage());
        }
        return false;
    }
}

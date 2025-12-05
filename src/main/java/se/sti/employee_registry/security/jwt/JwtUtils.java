package se.sti.employee_registry.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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

    public JwtUtils(@Value("${app.security-key}") String base64EncodedSecretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedSecretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(CustomUser customUser) {
        log.debug("Generate JWT token for {} with value: {}", customUser.getEmail(),customUser.getRoles());
        System.out.println("debug JWTToken: " + customUser.getEmail());
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

        log.debug("JWT generation successfully for user: {}", customUser.getEmail());
        return token;

    }

    public String getEmailFromJwtToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String email = claims.getSubject();
            log.debug("Extract email '{}' from token", email);
            return email;
        } catch (Exception e) {
            log.error("Failed to extract email from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String authToken) {

        if (authToken == null || authToken.isEmpty()) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);

            log.debug("Jwt token validated");
            return true;
        } catch (Exception e) {
            log.error("Jwt token validation failed: {}", e.getMessage());
        }
        return false;
    }


     public String extractJwtFromCookie(HttpServletRequest request){

        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if("authToken".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

package com.example.backend.common.provider;

import com.example.backend.dto.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {


    private Key secretKey;

    @Value("${jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyByte);
    }

    public String buildToken(CustomUserDetails customUserDetails, Long expiration) {
        Date expiryDate = new Date(new Date().getTime() + expiration);
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-id", customUserDetails.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(CustomUserDetails customUserDetails) {
        return buildToken(customUserDetails, accessExpiration);
    }

    public String createRefreshToken(CustomUserDetails customUserDetails) {
        return buildToken(customUserDetails, refreshExpiration);
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user-id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X_AUTH_TOKEN");
    }

}

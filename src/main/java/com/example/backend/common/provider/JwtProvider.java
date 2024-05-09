package com.example.backend.common.provider;

import com.example.backend.dto.oauth2.CustomOAuth2User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String buildToken(CustomOAuth2User customOAuth2User, Long expiration) {
        Date expiryDate = new Date(new Date().getTime() + expiration);

        return Jwts.builder()
                .setSubject(customOAuth2User.getUser().getNickname())
                .claim("userId", customOAuth2User.getUser().getUserId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(CustomOAuth2User customOAuth2User) {
        return buildToken(customOAuth2User, accessExpiration);
    }

    public String createRefreshToken(CustomOAuth2User customOAuth2User) {
        return buildToken(customOAuth2User, refreshExpiration);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user-id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
            return false;
        }
    }
/* TODO 토큰 유효성 검사

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


 */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }



}

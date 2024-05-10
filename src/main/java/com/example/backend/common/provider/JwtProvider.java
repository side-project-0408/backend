package com.example.backend.common.provider;

import com.example.backend.dto.oauth2.CustomOAuth2User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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



    public Boolean validToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
    }

}

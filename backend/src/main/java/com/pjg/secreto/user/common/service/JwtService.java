package com.pjg.secreto.user.common.service;

import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(User user) {
        return buildToken(new HashMap<>(), user, accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, refreshTokenExpiration);
    }

    public String generateAccessToken(ProviderUser providerUser) {
        return buildToken(new HashMap<>(), providerUser, accessTokenExpiration);
    }

    public String generateRefreshToken(ProviderUser providerUser) {
        return buildToken(new HashMap<>(), providerUser, refreshTokenExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, (c) -> c.get("nickname", String.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, (c) -> c.get("email", String.class));
    }


    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            return false;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return false;
//            throw new ExpiredJwtException(null, null, null);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            return false;
        }

    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User user,
            long expiration){
        addClaims(extraClaims, user);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            ProviderUser providerUser,
            long expiration){
        addClaims(extraClaims, providerUser);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static void addClaims(Map<String, Object> extraClaims, ProviderUser providerUser) {
        extraClaims.put("id", providerUser.getId());
        extraClaims.put("nickname", providerUser.getUsername());
        extraClaims.put("email", providerUser.getEmail());
        extraClaims.put("provider", providerUser.getProvider());
    }

    private static void addClaims(Map<String, Object> extraClaims, User providerUser) {
        extraClaims.put("id", providerUser.getId());
        extraClaims.put("nickname", providerUser.getNickname());
        extraClaims.put("email", providerUser.getEmail());
        extraClaims.put("provider", providerUser.getProvider());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

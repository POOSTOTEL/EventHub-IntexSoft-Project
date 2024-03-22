package com.Eventhub.EventHubIntexSoft.util;

import com.Eventhub.EventHubIntexSoft.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  @Value("${eventhub.app.jwt.access.secret-key}")
  private String SECRET_KEY;

  @Value("${eventhub.app.jwt.access.expiration-ms}")
  private int EXPIRATION_MS;

  public String generateJwtToken(User userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parse(token);
      return true;
    } catch (MalformedJwtException e) {
      System.out.println("Invalid JWT token: {}");
    } catch (ExpiredJwtException e) {
      System.out.println("JWT token is expired: {}");
    } catch (UnsupportedJwtException e) {
      System.out.println("JWT token is unsupported: {}");
    } catch (IllegalArgumentException e) {
      System.out.println("JWT claims string is empty: {}");
    }

    return false;
  }
}

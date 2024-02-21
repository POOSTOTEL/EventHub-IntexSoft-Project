package com.Eventhub.EventHubIntexSoft.service;

import io.jsonwebtoken.Claims;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JwtService {
  String extractEmail(String token);

  String generateToken(UserDetails userDetails);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);

  Claims extractAllClaims(String token);
}

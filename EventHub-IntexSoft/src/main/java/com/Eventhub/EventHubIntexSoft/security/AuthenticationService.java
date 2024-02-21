package com.Eventhub.EventHubIntexSoft.security;

import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.service.JwtService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    return Optional.of(
            User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new ArrayList<UserRole>(Arrays.asList(new UserRole(0L, "USER"))))
                .build())
        .map(userRepository::save)
        .map(jwtService::generateToken)
        .map(token -> AuthenticationResponse.builder().token(token).build())
        .orElseThrow(() -> new RuntimeException("Non-unique values."));
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    return Optional.ofNullable(userRepository.findUserByEmail(request.getEmail()))
        .map(jwtService::generateToken)
        .map(token -> AuthenticationResponse.builder().token(token).build())
        .orElseThrow(() -> new RuntimeException("User Not Found"));
  }
}

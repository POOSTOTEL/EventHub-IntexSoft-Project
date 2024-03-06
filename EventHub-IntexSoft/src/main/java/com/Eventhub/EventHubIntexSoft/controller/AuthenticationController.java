package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.TokenRefreshException;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.payload.request.LoginRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.SignupRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.TokenRefreshRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.JwtResponse;
import com.Eventhub.EventHubIntexSoft.payload.response.TokenRefreshResponse;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRoleRepository;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import com.Eventhub.EventHubIntexSoft.util.JwtUtils;
import com.Eventhub.EventHubIntexSoft.validator.UserValidator;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  protected final AuthenticationManager authenticationManager;
  protected final UserRepository userRepository;
  protected final UserRoleRepository userRoleRepository;
  protected final PasswordEncoder encoder;
  protected final RefreshTokenService refreshTokenService;
  protected final JwtUtils jwtUtils;
  protected final UserValidator userValidator;
  protected final UserMapper userMapper;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    SecurityContextHolder.getContext()
        .setAuthentication(
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword())));
    User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        JwtResponse.builder()
            .token(jwtUtils.generateJwtToken(userDetails))
            .type(refreshTokenService.createRefreshToken(userDetails.getUserId()).getToken())
            .id(userDetails.getUserId())
            .username(userDetails.getUsername())
            .email(userDetails.getEmail())
            .roles(
                userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .build());
  }

  @PostMapping("/signup")
  public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
      throws NonUniqValueException {
    userValidator.validateUniqUserName(signUpRequest.getUsername());
    userValidator.validateUniqEmail(signUpRequest.getEmail());
    return ResponseEntity.ok(
        userMapper.toUserDto(
            userRepository.save(
                User.builder()
                    .userName(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .roles(
                        Optional.of(signUpRequest.getRoles())
                            .map(
                                (strRoles) ->
                                    strRoles.stream()
                                        .map(userRoleRepository::findUserRoleByRole)
                                        .collect(Collectors.toSet()))
                            .orElseThrow(() -> new RuntimeException("roles is null")))
                    .build())));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    return refreshTokenService
        .findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(
            user -> {
              String token = jwtUtils.generateTokenFromUsername(user.getUsername());
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
        .orElseThrow(
            () ->
                new TokenRefreshException(
                    requestRefreshToken, "Refresh token is not in database!"));
  }
}

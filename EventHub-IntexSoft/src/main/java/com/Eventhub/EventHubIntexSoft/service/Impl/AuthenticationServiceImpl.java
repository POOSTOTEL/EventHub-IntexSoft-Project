package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.OAuth2Token;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.payload.request.GitHubUserRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.LoginRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.SignupRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.GitHubAuthorizeResponse;
import com.Eventhub.EventHubIntexSoft.payload.response.JwtResponse;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRoleRepository;
import com.Eventhub.EventHubIntexSoft.service.AuthenticationService;
import com.Eventhub.EventHubIntexSoft.service.OAuth2TokenService;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import com.Eventhub.EventHubIntexSoft.util.JwtUtils;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  protected final UserMapper userMapper;
  protected final PasswordEncoder encoder;
  protected final JwtUtils jwtUtils;
  protected final RefreshTokenService refreshTokenService;
  protected final UserRoleRepository userRoleRepository;
  protected final AuthenticationManager authenticationManager;
  protected final UserRepository userRepository;
  protected final OAuth2TokenService oAuth2TokenService;

  @Override
  public OAuth2TokenDto signUpGitHub(
      GitHubAuthorizeResponse gitHubAuthorizeResponse, GitHubUserRequest gitHubUserRequest) {
    Set<UserRole> roles = new HashSet<>();
    roles.add(userRoleRepository.findUserRoleByRole("USER"));
    return oAuth2TokenService.createOAuth2Token(
        OAuth2Token.builder()
            .accessToken(gitHubAuthorizeResponse.access_token())
            .vendorInfo("GIT_HUB")
            .user(
                User.builder()
                    .userName(
                        Objects.nonNull(gitHubUserRequest.name())
                            ? gitHubUserRequest.name()
                            : gitHubUserRequest.login())
                    .roles(roles)
                    .email(
                        Objects.nonNull(gitHubUserRequest.email()) ? gitHubUserRequest.email() : null)
                    .build())
            .build());
  }

  @Override
  public JwtResponse signInLocale(LoginRequest loginRequest) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password())));
    User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return JwtResponse.builder()
        .token(jwtUtils.generateJwtToken(userDetails))
        .type(refreshTokenService.createRefreshToken(userDetails.getUserId()).getToken())
        .id(userDetails.getUserId())
        .username(userDetails.getUsername())
        .email(userDetails.getEmail())
        .roles(
            userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .build();
  }

  @Override
  public UserDto signUpLocale(SignupRequest signUpRequest) {
    return userMapper.toUserDto(
        userRepository.save(
            User.builder()
                .userName(signUpRequest.username())
                .email(signUpRequest.email())
                .password(encoder.encode(signUpRequest.password()))
                .roles(
                    Optional.of(signUpRequest.roles())
                        .map(
                            (strRoles) ->
                                strRoles.stream()
                                    .map(userRoleRepository::findUserRoleByRole)
                                    .collect(Collectors.toSet()))
                        .orElseThrow(() -> new RuntimeException("roles is null")))
                .build()));
  }
}

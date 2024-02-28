package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.AuthRequestDto;
import com.Eventhub.EventHubIntexSoft.dto.JwtResponseDto;
import com.Eventhub.EventHubIntexSoft.dto.RefreshTokenRequestDto;
import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.AuthenticationService;
import com.Eventhub.EventHubIntexSoft.service.JwtService;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import com.Eventhub.EventHubIntexSoft.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  private final UserService userService;
  private final UserValidator userValidator;
  private final AuthenticationService authenticationService;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/registry")
  public JwtResponseDto RegistryAndGetToken(@RequestBody AuthRequestDto authRequestDto)
      throws NonUniqValueException {
    userValidator.validateUniqEmail(authRequestDto.getEmail());
    return authenticationService.registry(authRequestDto);
  }

  @PostMapping("/login")
  public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto)
      throws NotFoundException {
    userValidator.validateUserExistingByEmail(authRequestDto.getEmail());
    return authenticationService.authenticate(authRequestDto);
  }

  @PostMapping("/refreshToken")
  public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
    return refreshTokenService
        .findByToken(refreshTokenRequestDto.getRefreshToken())
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(
            userInfo -> {
              String accessToken = jwtService.generateToken(userInfo.getUsername());
              return JwtResponseDto.builder()
                  .accessToken(accessToken)
                  .refreshToken(refreshTokenRequestDto.getRefreshToken())
                  .build();
            })
        .orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
  }
}

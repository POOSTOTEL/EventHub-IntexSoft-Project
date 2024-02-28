package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.AuthRequestDto;
import com.Eventhub.EventHubIntexSoft.dto.JwtResponseDto;
import com.Eventhub.EventHubIntexSoft.entity.RefreshToken;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import com.Eventhub.EventHubIntexSoft.mapper.UserMapper;
import com.Eventhub.EventHubIntexSoft.service.AuthenticationService;
import com.Eventhub.EventHubIntexSoft.service.JwtService;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper;

  public JwtResponseDto registry(AuthRequestDto authRequestDto) {
    userService.createUser(
        userMapper.toUserDto(
            User.builder()
                .email(authRequestDto.getEmail())
                .password(passwordEncoder.encode(authRequestDto.getPassword()))
                .roles(new HashSet<>(Arrays.asList(new UserRole("USER"))))
                .build()));
    return authenticate(authRequestDto);
  }

  public JwtResponseDto authenticate(AuthRequestDto authRequestDto) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequestDto.getEmail(), authRequestDto.getPassword()));
    if (authentication.isAuthenticated()) {
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getEmail());
      return JwtResponseDto.builder()
          .accessToken(jwtService.generateToken(authRequestDto.getEmail()))
          .refreshToken(refreshToken.getToken())
          .build();
    } else {
      throw new UsernameNotFoundException("invalid user request..!!");
    }
  }
}

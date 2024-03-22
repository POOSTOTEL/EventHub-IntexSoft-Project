package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.payload.request.GitHubUserRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.LoginRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.SignupRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.GitHubAuthorizeResponse;
import com.Eventhub.EventHubIntexSoft.payload.response.JwtResponse;

public interface AuthenticationService {
  UserDto signUpLocale(SignupRequest signupRequest);

  JwtResponse signInLocale(LoginRequest loginRequest);

  OAuth2TokenDto signUpGitHub(
      GitHubAuthorizeResponse gitHubAuthorizeResponse, GitHubUserRequest gitHubUserRequest);
}

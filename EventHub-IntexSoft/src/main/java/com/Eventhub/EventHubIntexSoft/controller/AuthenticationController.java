package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.OAuth2TokenDto;
import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.exception.AuthenticationException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.payload.request.GitHubUserRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.LoginRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.SignupRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.TokenRefreshRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.GitHubAuthorizeResponse;
import com.Eventhub.EventHubIntexSoft.payload.response.JwtResponse;
import com.Eventhub.EventHubIntexSoft.payload.response.TokenRefreshResponse;
import com.Eventhub.EventHubIntexSoft.service.AuthenticationService;
import com.Eventhub.EventHubIntexSoft.service.RefreshTokenService;
import com.Eventhub.EventHubIntexSoft.validator.UserValidator;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
  protected final AuthenticationService authenticationService;
  protected final RefreshTokenService refreshTokenService;
  protected final UserValidator userValidator;

  @Value("${spring.security.oauth2.client.registration.github.client-id}")
  private String client_id;

  @Value("${spring.security.oauth2.client.registration.github.client-secret}")
  private String client_secret;

  private final WebClient gitHub = WebClient.create("https://github.com");
  private final WebClient gitHubApi = WebClient.create("https://api.github.com");

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUserByLocalData(
      @Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authenticationService.signInLocale(loginRequest));
  }

  @GetMapping("/signin/oauth2/github")
  public ResponseEntity<String> authenticateUserByGitHub() {
    String url =
        UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
            .queryParam("client_id", client_id)
            .queryParam("redirect_uri", "http://localhost:8082/signin/oauth2/github/auth")
            .queryParam("state", "ClumpFootZone")
            .toUriString();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", url);
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  @GetMapping("/signin/oauth2/github/auth")
  public ResponseEntity<OAuth2TokenDto> authenticateUserByGitHubCallback(
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "state", required = false) String state) {
    if (code != null && state != null) {
      if (state.equals("ClumpFootZone")) {
        GitHubAuthorizeResponse gitHubAuthorizeResponse =
            gitHub
                .post()
                .uri(
                    uriBuilder ->
                        uriBuilder
                            .path("/login/oauth/access_token")
                            .queryParam("client_id", client_id)
                            .queryParam("client_secret", client_secret)
                            .queryParam("code", code)
                            .queryParam("state", state)
                            .build())
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(GitHubAuthorizeResponse.class)
                .block();

        GitHubUserRequest gitHubUserRequest =
            gitHubApi
                .get()
                .uri("/user")
                .header(
                    "Authorization",
                    gitHubAuthorizeResponse.getToken_type()
                        + " "
                        + gitHubAuthorizeResponse.getAccess_token())
                .retrieve()
                .bodyToMono(GitHubUserRequest.class)
                .block();
        // userValidator.validateUserExistingByEmail(Objects.nonNull());
        if (Objects.nonNull(gitHubUserRequest.login)) {
          return ResponseEntity.ok(
              authenticationService.signUpGitHub(gitHubAuthorizeResponse, gitHubUserRequest));
        } else {
          throw new AuthenticationException("No response was received from the GitHubAPI server.");
        }
      } else {
        throw new AuthenticationException("An attempt was made to fake an authorization request.");
      }
    } else {
      throw new AuthenticationException(
          "Check the correctness of the generated request, in particular the correctness of filling in the request parameters.");
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
      throws NonUniqValueException {
    userValidator.validateUniqUserName(signUpRequest.getUsername());
    userValidator.validateUniqEmail(signUpRequest.getEmail());
    return ResponseEntity.ok(authenticationService.signUpLocale(signUpRequest));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<TokenRefreshResponse> refreshToken(
      @Valid @RequestBody TokenRefreshRequest request) {
    return ResponseEntity.ok(refreshTokenService.giveAccessToken(request));
  }
}

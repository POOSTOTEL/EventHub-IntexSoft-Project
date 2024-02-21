package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.security.AuthenticationRequest;
import com.Eventhub.EventHubIntexSoft.security.AuthenticationResponse;
import com.Eventhub.EventHubIntexSoft.security.AuthenticationService;
import com.Eventhub.EventHubIntexSoft.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(authenticationService.register(registerRequest));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest authenticationRequest) {
    return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
  }
}

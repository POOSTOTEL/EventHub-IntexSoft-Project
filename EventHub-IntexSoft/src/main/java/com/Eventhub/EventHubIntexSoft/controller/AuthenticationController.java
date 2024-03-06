package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.entity.UserRole;
import com.Eventhub.EventHubIntexSoft.payload.request.LoginRequest;
import com.Eventhub.EventHubIntexSoft.payload.request.SignupRequest;
import com.Eventhub.EventHubIntexSoft.payload.response.JwtResponse;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRoleRepository;
import com.Eventhub.EventHubIntexSoft.util.JwtUtils;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

  protected final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    User userDetails = (User) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new JwtResponse(
            jwt,
            userDetails.getUserId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (Optional.ofNullable(userRepository.findUserByUserName(signUpRequest.getUsername()))
        .isPresent()) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (Optional.ofNullable(userRepository.findUserByEmail(signUpRequest.getEmail())).isPresent()) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }
    User user =
        User.builder()
            .userName(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();

    Set<String> strRoles = signUpRequest.getRole();
    Set<UserRole> roles = new HashSet<>();

    if (strRoles == null) {
      roles.add(userRoleRepository.findUserRoleByRole("USER"));
    } else {
      strRoles.forEach(
          role -> {
            if (role.equals("ADMIN")) {
              roles.add(userRoleRepository.findUserRoleByRole("ADMIN"));
            } else {
              roles.add(userRoleRepository.findUserRoleByRole("USER"));
            }
          });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully!");
  }
}

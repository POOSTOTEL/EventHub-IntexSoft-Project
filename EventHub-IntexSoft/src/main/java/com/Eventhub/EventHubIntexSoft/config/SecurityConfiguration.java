package com.Eventhub.EventHubIntexSoft.config;

import com.Eventhub.EventHubIntexSoft.filter.AuthTokenFilter;
import com.Eventhub.EventHubIntexSoft.handler.AuthEntryPointJwt;
import com.Eventhub.EventHubIntexSoft.service.Impl.UserServiceImpl;
import com.Eventhub.EventHubIntexSoft.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  protected final UserDetailsService userDetailsService;
  protected final AuthEntryPointJwt unauthorizedHandler;
  protected final JwtUtils jwtUtils;
  protected final UserServiceImpl userService;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter(jwtUtils, userService);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            req -> {
              req.requestMatchers(
                              HttpMethod.GET,
                              "/comment/{commentId}",
                              "/comment/all",
                              "/event/{eventId}",
                              "/event/all",
                              "/participant/{participantId}",
                              "/participant/all",
                              "/user/{userId}",
                              "/user/all")
                      .hasAuthority("USER")
                      .requestMatchers(HttpMethod.POST, "/comment", "/event", "/participant", "/user")
                      .hasAuthority("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/comment", "/event", "/participant", "/user")
                      .hasAuthority("ADMIN")
                      .requestMatchers(HttpMethod.PATCH, "/comment", "/event", "/participant", "/user")
                      .hasAuthority("ADMIN")
                      .requestMatchers(
                              HttpMethod.DELETE,
                              "/comment/{commentId}",
                              "/event/{eventId}",
                              "/participant/{participantId}",
                              "/user/{userId}")
                      .hasAuthority("ADMIN")
                      .requestMatchers(
                              HttpMethod.POST, "/signup", "/signin", "/refreshtoken", "/signin/oauth2/github", "/signin/oauth2/github/auth")
                      .permitAll()
                      .requestMatchers(
                              HttpMethod.GET, "/signin/oauth2/github", "/signin/oauth2/github/auth")
                      .permitAll()
                      .anyRequest()
                      .authenticated();
            })
        .sessionManagement(
            sessionAuthStrategy ->
                sessionAuthStrategy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(
            authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

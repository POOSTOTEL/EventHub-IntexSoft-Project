package com.Eventhub.EventHubIntexSoft.config;

import com.Eventhub.EventHubIntexSoft.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
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
                  .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/registry", "auth/refresh")
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .sessionManagement(
            sessionAuthStrategy ->
                sessionAuthStrategy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

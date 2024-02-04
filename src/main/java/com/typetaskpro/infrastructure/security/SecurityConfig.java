package com.typetaskpro.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private SecurityFilter securityFilter;

  public SecurityConfig(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) 
    throws Exception {

    return httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/projects").permitAll()
        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
        .requestMatchers(HttpMethod.PUT, "/users/{id}").authenticated()
        .requestMatchers(HttpMethod.GET, "/projects").authenticated()
        .requestMatchers(HttpMethod.POST, "/projects").authenticated()
        .requestMatchers(HttpMethod.DELETE, "/projects/{id}").authenticated()
        .requestMatchers(HttpMethod.PUT, "/projects/{id}").authenticated()
        .anyRequest().permitAll()
      )
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

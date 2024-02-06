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

  private final SecurityFilter securityFilter;

  public SecurityConfig(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  /**
   * Configures a security filter chain for the application.
   *
   * @param httpSecurity allows configuring web based security for specific http requests
   *                     and paths.
   * @return a SecurityFilterChain instance to apply security configurations.
   * @throws Exception if an exception occurs.
   */
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

  /**
   * Creates a bean with AuthenticationManager type.
   *
   * @param authenticationConfiguration is a AuthenticationConfiguration instance which contains
   *                                    authentication configuration.
   * @return an instance of AuthenticationManager.
   * @throws Exception if an exception occurs.
   */
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Creates a bean with PasswordEncoder type.
   *
   * @return BCryptPasswordEncoder instance as PasswordEncoder.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

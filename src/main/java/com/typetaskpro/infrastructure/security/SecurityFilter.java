package com.typetaskpro.infrastructure.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.typetaskpro.core.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final TokenService tokenService;

  public SecurityFilter(
    UserRepository userRepository,
    TokenService tokenService
  ) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }

  /**
   * Custom implementation of doFilterInternal from OncePerRequestFilter class.
   * Used to authenticate a user based on a token retrieved from a request.
   *
   * @param request expected to contain Authentication Token.
   * @param response to be sent.
   * @param filterChain the remain filter to procced after this method.
   * @throws IOException if a IO errors occur.
   * @throws ServletException if a general servlet error occurs.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
    throws IOException, ServletException{
    
    var token = recoverToken(request);

    if(token != null) {
      var login = tokenService.validateToken(token);
      Optional<UserDetails> user = userRepository.findByUsername(login);
      if(user.isEmpty()) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "you cant access this page");
        return;
      }

      var auth = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Used to recover a token from a request.
   *
   * @param request expected to contain 'Authorization' header with a 'Bearer Token'
   *                expected to contain a valid JWT Token.
   * @return the JWT Token as a String or null if the header is not present.
   */
  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    return authHeader == null ? null : authHeader.replace("Bearer ", "");
  }
}

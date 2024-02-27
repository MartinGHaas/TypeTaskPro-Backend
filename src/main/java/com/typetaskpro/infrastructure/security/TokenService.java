package com.typetaskpro.infrastructure.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class TokenService {
  
  private final Dotenv dotenv = Dotenv.load();
  private final String jwtSecret = dotenv.get("JWT_SECRET");

  /**
   * Generates a new JWT Token.
   *
   * @param username for creating a new JWT Token.
   * @return a new JWT authenticated token.
   */
  public String generateToken(String username, long id) {
    
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
      String token = JWT.create()
                        .withIssuer("auth-api")
                        .withSubject(username)
                        .withClaim("uid", id)
                        .withExpiresAt(generateExpirationDate())
                        .sign(algorithm);
      return token;
    } catch (JWTCreationException e) {
      throw new RuntimeException("ERROR WHILE CREATING JWT", e);
    }
  }

  /**
   * Validates JWT Token.
   *
   * @param token to be validated by the method.
   * @return username or an empty String.
   */
  public String validateToken(String token) {
    
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
      return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
    } catch(JWTVerificationException e) {
      return "";
    }
  }

  /**
   * Generates JWT Token expiration date.
   *
   * @return the expiration date for the JWT Token.
   */
  private Instant generateExpirationDate() {
    return LocalDateTime.now()
                    .plusMonths(1)
                    .toInstant(ZoneOffset.of("-03:00"));
  }
}

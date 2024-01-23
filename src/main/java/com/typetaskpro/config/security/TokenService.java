package com.typetaskpro.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.typetaskpro.domain.user.model.User;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class TokenService {
  
  private Dotenv dotenv = Dotenv.load();
  private String jwtSecret = dotenv.get("JWT_SECRET");

  public String generateToken(User user) {
    
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
      String token = JWT.create()
                        .withIssuer("auth-api")
                        .withSubject(user.getUsername())
                        .withExpiresAt(generateExpirationDate())
                        .sign(algorithm);
      return token;
    } catch (JWTCreationException e) {
      throw new RuntimeException("ERROR WHILE CREATING JWT", e);
    }
  }

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

  private Instant generateExpirationDate() {
    return LocalDateTime.now()
                    .plusMonths(1)
                    .toInstant(ZoneOffset.of("-03:00"));
  }
}

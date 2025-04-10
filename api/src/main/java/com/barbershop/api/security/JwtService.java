package com.barbershop.api.security;

import com.barbershop.api.domain.UsuarioEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET_KEY = "UmaChaveMuitoSecretaAquiUmaChaveMuitoSecretaAqui"; // pelo menos 256 bits
  private static final long EXPIRATION_TIME = 86400000;

  private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

  public String generateToken(UsuarioEntity usuario) {
    return Jwts.builder()
        .claim("id", usuario.getId())
        .setSubject(usuario.getUsername())
        .claim("role", usuario.getRole().name())
        .claim("email", usuario.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
//  public Long getUserIdFromToken(String token) {
//    return Long.valueOf(Jwts.parserBuilder()
//        .setSigningKey(key)
//        .build()
//        .parseClaimsJws(token)
//        .getBody()
//        .get("id").toString());
//  }
}

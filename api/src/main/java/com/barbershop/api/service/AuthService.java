package com.barbershop.api.service;

import com.barbershop.api.dto.request.AuthRequest;
import com.barbershop.api.dto.response.AuthResponse;
import com.barbershop.api.domain.UsuarioEntity;
import com.barbershop.api.repository.UsuarioRepository;
import com.barbershop.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UsuarioRepository usuarioRepository;
  private final JwtService jwtService;

  public AuthResponse login(AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
      );

      UsuarioEntity user = usuarioRepository.findByEmail(request.getEmail())
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      String token = jwtService.generateToken(user);
      return new AuthResponse(token, user.getId());
    } catch (AuthenticationException e) {
      throw new RuntimeException("Email ou senha inválidos");
    }
  }
}

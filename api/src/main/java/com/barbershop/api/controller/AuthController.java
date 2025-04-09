package com.barbershop.api.controller;

import com.barbershop.api.dto.request.AuthRequest;
import com.barbershop.api.dto.response.AuthResponse;
import com.barbershop.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Rota para usuário logar")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  @Operation(summary = "Usuário deve logar")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }
}

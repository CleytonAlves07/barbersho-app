package com.barbershop.api.controller;

import com.barbershop.api.dto.UsuarioRequest;
import com.barbershop.api.dto.UsuarioResponse;
import com.barbershop.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários (clientes e barbeiros)")
public class UsuarioController {
  private final UsuarioService usuarioService;

  @PostMapping
  @Operation(summary = "Criar novo usuário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "409", description = "Username ou email já existente")
  })
  public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody UsuarioRequest request) {
    UsuarioResponse response = usuarioService.criarUsuario(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  @Operation(summary = "Listar todos os usuários")
  @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
  public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
    List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();
    return ResponseEntity.ok(usuarios);
  }


  @GetMapping("/{id}")
  @Operation(summary = "Mostra o usuário selecionado por ID")
  public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID id) {
    return ResponseEntity.ok(usuarioService.buscarPorId(id));
  }


  @PutMapping("/{id}")
  @Operation(summary = "Modifica o usuário selecionado por ID")
  public ResponseEntity<UsuarioResponse> atualizarUsuario(
      @PathVariable UUID id,
      @Valid @RequestBody UsuarioRequest request
  ) {
    return ResponseEntity.ok(usuarioService.atualizarUsuario(id, request));
  }


  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta o usuário selecionado por ID")
  public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
    usuarioService.deletarUsuario(id);
    return ResponseEntity.noContent().build();
  }

}

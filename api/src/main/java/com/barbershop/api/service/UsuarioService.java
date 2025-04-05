package com.barbershop.api.service;

import com.barbershop.api.domain.UsuarioEntity;
import com.barbershop.api.dto.UsuarioRequest;
import com.barbershop.api.dto.UsuarioResponse;
import com.barbershop.api.exception.UsuarioExistenteException;
import com.barbershop.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public UsuarioResponse criarUsuario(UsuarioRequest request) {
    if (usuarioRepository.existsByUsername(request.getUsername())) {
      throw new UsuarioExistenteException("Username já está em uso");
    }

    if (usuarioRepository.existsByEmail(request.getEmail())) {
      throw new UsuarioExistenteException("Email já está em uso");
    }

    UsuarioEntity usuario = UsuarioEntity.builder()
        .nome(request.getNome())
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .email(request.getEmail())
        .telefone(request.getTelefone())
        .role(request.getRole())
        .build();

    usuario = usuarioRepository.save(usuario);
    return mapToResponse(usuario);
  }

  public List<UsuarioResponse> listarUsuarios() {
    return usuarioRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  private UsuarioResponse mapToResponse(UsuarioEntity usuario) {
    return UsuarioResponse.builder()
        .id(usuario.getId())
        .nome(usuario.getNome())
        .username(usuario.getUsername())
        .email(usuario.getEmail())
        .telefone(usuario.getTelefone())
        .role(usuario.getRole())
        .build();
  }
  public UsuarioResponse buscarPorId(UUID id) {
    UsuarioEntity usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    return mapToResponse(usuario);
  }

  public UsuarioResponse atualizarUsuario(UUID id, UsuarioRequest request) {
    UsuarioEntity usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuario.setNome(request.getNome());
    usuario.setTelefone(request.getTelefone());

    if (!usuario.getUsername().equals(request.getUsername()) &&
        usuarioRepository.existsByUsername(request.getUsername())) {
      throw new UsuarioExistenteException("Username já em uso");
    }
    if (!usuario.getEmail().equals(request.getEmail()) &&
        usuarioRepository.existsByEmail(request.getEmail())) {
      throw new UsuarioExistenteException("Email já em uso");
    }

    usuario.setUsername(request.getUsername());
    usuario.setEmail(request.getEmail());
    usuario.setRole(request.getRole());

    if (request.getPassword() != null && !request.getPassword().isBlank()) {
      usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    usuario = usuarioRepository.save(usuario);
    return mapToResponse(usuario);
  }

  public void deletarUsuario(UUID id) {
    if (!usuarioRepository.existsById(id)) {
      throw new RuntimeException("Usuário não encontrado");
    }
    usuarioRepository.deleteById(id);
  }


}

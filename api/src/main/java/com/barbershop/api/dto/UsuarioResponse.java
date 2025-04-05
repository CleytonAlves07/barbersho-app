package com.barbershop.api.dto;

import com.barbershop.api.domain.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
  private UUID id;
  private String nome;
  private String username;
  private String email;
  private String telefone;
  private UsuarioEntity.UsuarioRole role;
}

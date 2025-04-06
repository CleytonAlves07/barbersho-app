package com.barbershop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponse {

  @Schema(example = "3f6e0f4e-b97a-45dc-b4bb-123456789abc")
  private UUID id;

  @Schema(example = "2025-04-05T09:00:00")
  private LocalDateTime dataHora;

  @Schema(example = "PENDENTE")
  private String status;

  @Schema(description = "Informações do cliente que agendou")
  private UsuarioResponse cliente;

  @Schema(description = "Informações da agenda selecionada")
  private AgendaResponse agenda;
}


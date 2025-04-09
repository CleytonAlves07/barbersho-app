package com.barbershop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaResponse {

  @Schema(example = "3f6e0f4e-b97a-45dc-b4bb-123456789abc")
  private UUID id;

  @Schema(example = "2025-04-05T09:00:00")
  private LocalDateTime horarioInicio;

  @Schema(example = "2025-04-05T10:00:00")
  private LocalDateTime horarioFim;

  @Schema(description = "Informações do barbeiro")
  private UsuarioResponse barbeiro;
}


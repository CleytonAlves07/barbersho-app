package com.barbershop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaRequest {

  @Schema(example = "2025-04-05T09:00:00")
  @NotNull(message = "Horário de início é obrigatório")
  private LocalDateTime horarioInicio;

  @Schema(example = "2025-04-05T10:00:00")
  @NotNull(message = "Horário de fim é obrigatório")
  private LocalDateTime horarioFim;

  @Schema(example = "a6d8f714-bd7e-4b4d-b9c0-6c9d0ec0f5f3", description = "ID do barbeiro")
  @NotNull(message = "ID do barbeiro é obrigatório")
  private UUID barbeiroId;
}


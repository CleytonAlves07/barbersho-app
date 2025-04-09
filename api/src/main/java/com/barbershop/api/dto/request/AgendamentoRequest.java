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
public class AgendamentoRequest {

  @Schema(example = "2025-04-05T09:00:00")
  @NotNull(message = "Data e hora do agendamento são obrigatórias")
  private LocalDateTime dataHora;

  @Schema(example = "PENDENTE", description = "Status do agendamento, como PENDENTE, CONFIRMADO, CANCELADO")
  @NotNull(message = "Status é obrigatório")
  private String status;

  @Schema(example = "a6d8f714-bd7e-4b4d-b9c0-6c9d0ec0f5f3", description = "ID do cliente que está agendando")
  @NotNull(message = "ID do cliente é obrigatório")
  private UUID clienteId;

  @Schema(example = "9d94a776-6dc5-4d5a-9423-3e0eab5f3c2b", description = "ID da agenda selecionada")
  @NotNull(message = "ID da agenda é obrigatório")
  private UUID agendaId;
}


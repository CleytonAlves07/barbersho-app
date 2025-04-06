package com.barbershop.api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agendamentos")
public class AgendamentoEntity {
  @Id
  @GeneratedValue
  private UUID id;

  private LocalDateTime dataHora;
  private String status;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private UsuarioEntity cliente;

  @ManyToOne
  @JoinColumn(name = "agenda_id")
  private AgendaEntity agenda;
}


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
@Table(name = "agendas")
public class AgendaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private LocalDateTime horarioInicio;

  @Column(nullable = false)
  private LocalDateTime horarioFim;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "barbeiro_id", nullable = false)
  private UsuarioEntity barbeiro;
}

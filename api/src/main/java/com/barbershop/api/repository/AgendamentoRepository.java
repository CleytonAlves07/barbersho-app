package com.barbershop.api.repository;

import com.barbershop.api.domain.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, UUID> {
  List<AgendamentoEntity> findByClienteId(UUID clienteId);
  List<AgendamentoEntity> findByAgendaBarbeiroId(UUID barbeiroId);
  boolean existsByAgendaId(UUID agendaId);
}


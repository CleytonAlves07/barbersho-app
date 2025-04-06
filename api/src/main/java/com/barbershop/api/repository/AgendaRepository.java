package com.barbershop.api.repository;

import com.barbershop.api.domain.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AgendaRepository extends JpaRepository<AgendaEntity, UUID> {
  List<AgendaEntity> findByBarbeiroId(UUID barbeiroId);
  @Query("SELECT a FROM AgendaEntity a JOIN FETCH a.barbeiro")
  List<AgendaEntity> findAllWithBarbeiro();
}


package com.barbershop.api.repository;

import com.barbershop.api.domain.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
  Optional<UsuarioEntity> findByUsername(String username);
  Optional<UsuarioEntity> findByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}

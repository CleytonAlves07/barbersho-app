package com.barbershop.api.controller;

import com.barbershop.api.dto.AgendaRequest;
import com.barbershop.api.dto.AgendaResponse;
import com.barbershop.api.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agendas")
@RequiredArgsConstructor
@Tag(name = "Agendas", description = "API para gerenciamento de agendas de barbeiros")
public class AgendaController {

  private final AgendaService agendaService;

  @PostMapping
  @Operation(summary = "Criar uma nova agenda para um barbeiro")
  public ResponseEntity<AgendaResponse> criarAgenda(@Valid @RequestBody AgendaRequest request) {
    return ResponseEntity.ok(agendaService.criarAgenda(request));
  }

  @GetMapping
  @Operation(summary = "Listar todas as agendas")
  public ResponseEntity<List<AgendaResponse>> listarAgendas() {
    return ResponseEntity.ok(agendaService.listarAgendas());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar uma agenda por ID")
  public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable UUID id) {
    return ResponseEntity.ok(agendaService.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar uma agenda existente")
  public ResponseEntity<AgendaResponse> atualizarAgenda(
      @PathVariable UUID id,
      @Valid @RequestBody AgendaRequest request) {
    return ResponseEntity.ok(agendaService.atualizarAgenda(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Excluir uma agenda")
  public ResponseEntity<Void> deletarAgenda(@PathVariable UUID id) {
    agendaService.deletarAgenda(id);
    return ResponseEntity.noContent().build();
  }
}

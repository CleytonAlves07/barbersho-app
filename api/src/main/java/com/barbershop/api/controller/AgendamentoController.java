package com.barbershop.api.controller;

import com.barbershop.api.dto.AgendamentoRequest;
import com.barbershop.api.dto.AgendamentoResponse;
import com.barbershop.api.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "API para gerenciamento de agendamentos de clientes")
public class AgendamentoController {

  private final AgendamentoService agendamentoService;

  @PostMapping
  @Operation(summary = "Criar um novo agendamento")
  public ResponseEntity<AgendamentoResponse> criarAgendamento(@Valid @RequestBody AgendamentoRequest request) {
    return ResponseEntity.ok(agendamentoService.criarAgendamento(request));
  }

  @GetMapping
  @Operation(summary = "Listar todos os agendamentos")
  public ResponseEntity<List<AgendamentoResponse>> listarAgendamentos() {
    return ResponseEntity.ok(agendamentoService.listarAgendamentos());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar agendamento por ID")
  public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable UUID id) {
    return ResponseEntity.ok(agendamentoService.buscarPorId(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar um agendamento existente")
  public ResponseEntity<AgendamentoResponse> atualizarAgendamento(
      @PathVariable UUID id,
      @Valid @RequestBody AgendamentoRequest request) {
    return ResponseEntity.ok(agendamentoService.atualizarAgendamento(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletar um agendamento")
  public ResponseEntity<Void> deletarAgendamento(@PathVariable UUID id) {
    agendamentoService.deletarAgendamento(id);
    return ResponseEntity.noContent().build();
  }
}

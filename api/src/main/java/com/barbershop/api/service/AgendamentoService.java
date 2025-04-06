package com.barbershop.api.service;

import com.barbershop.api.domain.AgendamentoEntity;
import com.barbershop.api.domain.AgendaEntity;
import com.barbershop.api.domain.UsuarioEntity;
import com.barbershop.api.dto.AgendaResponse;
import com.barbershop.api.dto.AgendamentoRequest;
import com.barbershop.api.dto.AgendamentoResponse;
import com.barbershop.api.dto.UsuarioResponse;
import com.barbershop.api.repository.AgendamentoRepository;
import com.barbershop.api.repository.AgendaRepository;
import com.barbershop.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

  private final AgendamentoRepository agendamentoRepository;
  private final UsuarioRepository usuarioRepository;
  private final AgendaRepository agendaRepository;

  public AgendamentoResponse criarAgendamento(AgendamentoRequest request) {
    return salvarAgendamento(new AgendamentoEntity(), request);
  }

  public List<AgendamentoResponse> listarAgendamentos() {
    return agendamentoRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  public AgendamentoResponse buscarPorId(UUID id) {
    AgendamentoEntity agendamento = agendamentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    return mapToResponse(agendamento);
  }

  public AgendamentoResponse atualizarAgendamento(UUID id, AgendamentoRequest request) {
    AgendamentoEntity agendamento = agendamentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    return salvarAgendamento(agendamento, request);
  }

  public void deletarAgendamento(UUID id) {
    AgendamentoEntity agendamento = agendamentoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    agendamentoRepository.delete(agendamento);
  }

  private AgendamentoResponse salvarAgendamento(AgendamentoEntity agendamento, AgendamentoRequest request) {
    UsuarioEntity cliente = usuarioRepository.findById(request.getClienteId())
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

    AgendaEntity agenda = agendaRepository.findById(request.getAgendaId())
        .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

    agendamento.setDataHora(request.getDataHora());
    agendamento.setStatus(request.getStatus());
    agendamento.setCliente(cliente);
    agendamento.setAgenda(agenda);

    agendamento = agendamentoRepository.save(agendamento);

    return mapToResponse(agendamento);
  }

  private AgendamentoResponse mapToResponse(AgendamentoEntity agendamento) {
    return AgendamentoResponse.builder()
        .id(agendamento.getId())
        .dataHora(agendamento.getDataHora())
        .status(agendamento.getStatus())
        .cliente(UsuarioResponse.builder()
            .id(agendamento.getCliente().getId())
            .nome(agendamento.getCliente().getNome())
            .username(agendamento.getCliente().getUsername())
            .email(agendamento.getCliente().getEmail())
            .telefone(agendamento.getCliente().getTelefone())
            .role(agendamento.getCliente().getRole())
            .build())
        .agenda(AgendaResponse.builder()
            .id(agendamento.getAgenda().getId())
            .horarioInicio(agendamento.getAgenda().getHorarioInicio())
            .horarioFim(agendamento.getAgenda().getHorarioFim())
            .barbeiro(UsuarioResponse.builder()
                .id(agendamento.getAgenda().getBarbeiro().getId())
                .nome(agendamento.getAgenda().getBarbeiro().getNome())
                .username(agendamento.getAgenda().getBarbeiro().getUsername())
                .email(agendamento.getAgenda().getBarbeiro().getEmail())
                .telefone(agendamento.getAgenda().getBarbeiro().getTelefone())
                .role(agendamento.getAgenda().getBarbeiro().getRole())
                .build())
            .build())
        .build();
  }
}

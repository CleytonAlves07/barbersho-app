package com.barbershop.api.service;

import com.barbershop.api.domain.AgendaEntity;
import com.barbershop.api.domain.UsuarioEntity;
import com.barbershop.api.dto.AgendaRequest;
import com.barbershop.api.dto.AgendaResponse;
import com.barbershop.api.dto.UsuarioResponse;
import com.barbershop.api.exception.BusinessException;
import com.barbershop.api.repository.AgendaRepository;
import com.barbershop.api.repository.AgendamentoRepository;
import com.barbershop.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendaService {

  private final AgendaRepository agendaRepository;
  private final AgendamentoRepository agendamentoRepository;
  private final UsuarioRepository usuarioRepository;

  public AgendaResponse criarAgenda(AgendaRequest request) {
    UsuarioEntity barbeiro = usuarioRepository.findById(request.getBarbeiroId())
        .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

    AgendaEntity agenda = AgendaEntity.builder()
        .horarioInicio(request.getHorarioInicio())
        .horarioFim(request.getHorarioFim())
        .barbeiro(barbeiro)
        .build();

    agenda = agendaRepository.save(agenda);
    return mapToResponse(agenda);
  }

  public List<AgendaResponse> listarAgendas() {
    return agendaRepository.findAllWithBarbeiro().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  public AgendaResponse buscarPorId(UUID id) {
    AgendaEntity agenda = agendaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));
    return mapToResponse(agenda);
  }

  public AgendaResponse atualizarAgenda(UUID id, AgendaRequest request) {
    AgendaEntity agenda = agendaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

    UsuarioEntity barbeiro = usuarioRepository.findById(request.getBarbeiroId())
        .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

    agenda.setHorarioInicio(request.getHorarioInicio());
    agenda.setHorarioFim(request.getHorarioFim());
    agenda.setBarbeiro(barbeiro);

    agenda = agendaRepository.save(agenda);
    return mapToResponse(agenda);
  }

  public void deletarAgenda(UUID id) {
    if (agendamentoRepository.existsByAgendaId(id)) {
      throw new BusinessException("Não é possível excluir a agenda, pois existem agendamentos vinculados.");
    }
    if (!agendaRepository.existsById(id)) {
      throw new RuntimeException("Agenda não encontrada");
    }
    agendaRepository.deleteById(id);
  }

  private AgendaResponse mapToResponse(AgendaEntity agenda) {
    UsuarioEntity barbeiro = agenda.getBarbeiro();

    UsuarioResponse barbeiroResponse = UsuarioResponse.builder()
        .id(barbeiro.getId())
        .nome(barbeiro.getNome())
        .username(barbeiro.getUsername())
        .email(barbeiro.getEmail())
        .telefone(barbeiro.getTelefone())
        .role(barbeiro.getRole())
        .build();

    return AgendaResponse.builder()
        .id(agenda.getId())
        .horarioInicio(agenda.getHorarioInicio())
        .horarioFim(agenda.getHorarioFim())
        .barbeiro(barbeiroResponse)
        .build();
  }
}

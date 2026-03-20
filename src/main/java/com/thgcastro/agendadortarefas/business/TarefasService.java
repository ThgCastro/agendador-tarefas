package com.thgcastro.agendadortarefas.business;

import com.thgcastro.agendadortarefas.business.dto.TarefasDTO;
import com.thgcastro.agendadortarefas.business.mapper.TarefasConverter;
import com.thgcastro.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.thgcastro.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.thgcastro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.thgcastro.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.thgcastro.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.thgcastro.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscarTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                             LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscarTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefasDTO(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Tarefa não encontrada pelo id " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status da tarefa", e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefasUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar o status da tarefa", e.getCause());
        }
    }
}

package com.thgcastro.agendadortarefas.business;

import com.thgcastro.agendadortarefas.business.dto.TarefasDTO;
import com.thgcastro.agendadortarefas.business.mapper.TarefasConverter;
import com.thgcastro.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.thgcastro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.thgcastro.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.thgcastro.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }
}

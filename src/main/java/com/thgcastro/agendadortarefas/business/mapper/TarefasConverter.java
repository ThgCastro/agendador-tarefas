package com.thgcastro.agendadortarefas.business.mapper;

import com.thgcastro.agendadortarefas.business.dto.TarefasDTO;
import com.thgcastro.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);

}

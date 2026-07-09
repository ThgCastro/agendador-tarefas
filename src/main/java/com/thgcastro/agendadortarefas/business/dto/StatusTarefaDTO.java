package com.thgcastro.agendadortarefas.business.dto;

import com.thgcastro.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusTarefaDTO {

    private String id;
    private StatusNotificacaoEnum statusNotificacaoEnum;
}

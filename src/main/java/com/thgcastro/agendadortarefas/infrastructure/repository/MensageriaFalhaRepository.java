package com.thgcastro.agendadortarefas.infrastructure.repository;

import com.thgcastro.agendadortarefas.infrastructure.entity.MensageriaFalhaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensageriaFalhaRepository extends MongoRepository<MensageriaFalhaEntity, String> {


}

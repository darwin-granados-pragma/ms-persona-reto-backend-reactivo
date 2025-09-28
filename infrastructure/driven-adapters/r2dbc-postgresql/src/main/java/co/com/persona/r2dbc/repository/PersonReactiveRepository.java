package co.com.persona.r2dbc.repository;

import co.com.persona.r2dbc.entity.PersonEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PersonReactiveRepository extends ReactiveCrudRepository<PersonEntity, String>,
    ReactiveQueryByExampleExecutor<PersonEntity> {

  Mono<PersonEntity> findByEmail(String email);
}

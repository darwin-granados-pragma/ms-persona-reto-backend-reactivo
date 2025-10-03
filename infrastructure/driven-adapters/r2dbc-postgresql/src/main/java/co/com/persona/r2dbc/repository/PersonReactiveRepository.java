package co.com.persona.r2dbc.repository;

import co.com.persona.r2dbc.entity.PersonEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonReactiveRepository extends ReactiveCrudRepository<PersonEntity, String>,
    ReactiveQueryByExampleExecutor<PersonEntity> {

  Mono<PersonEntity> findByEmail(String email);

  @Query("""
      SELECT p.*
      FROM persona p
      INNER JOIN bootcamp_persona bp ON p.id_persona = bp.id_persona
      WHERE bp.id_bootcamp = :idBootcamp
      """
  )
  Flux<PersonEntity> findByIdBootcamp(String idBootcamp);
}

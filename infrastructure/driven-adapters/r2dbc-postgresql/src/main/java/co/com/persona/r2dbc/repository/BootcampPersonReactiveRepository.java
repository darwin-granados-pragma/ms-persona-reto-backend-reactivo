package co.com.persona.r2dbc.repository;

import co.com.persona.r2dbc.entity.BootcampPersonEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BootcampPersonReactiveRepository extends
    ReactiveCrudRepository<BootcampPersonEntity, String>,
    ReactiveQueryByExampleExecutor<BootcampPersonEntity> {

  Mono<Long> countByIdBootcamp(String idBootcamp);
}

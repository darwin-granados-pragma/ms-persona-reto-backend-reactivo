package co.com.persona.r2dbc.adapter;

import co.com.persona.model.bootcamp.BootcampPerson;
import co.com.persona.model.gateways.BootcampPersonRepository;
import co.com.persona.r2dbc.entity.BootcampPersonEntity;
import co.com.persona.r2dbc.helper.ReactiveAdapterOperations;
import co.com.persona.r2dbc.repository.BootcampPersonReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class BootcampPersonReactiveRepositoryAdapter extends
    ReactiveAdapterOperations<BootcampPerson, BootcampPersonEntity, String, BootcampPersonReactiveRepository> implements
    BootcampPersonRepository {

  public BootcampPersonReactiveRepositoryAdapter(BootcampPersonReactiveRepository repository,
      ObjectMapper mapper) {
    super(repository, mapper, d -> mapper.map(d, BootcampPerson.class));
  }

  @Override
  public Mono<BootcampPerson> save(BootcampPerson data) {
    log.info("Saving bootcamp-person with name");
    return super
        .save(data)
        .doOnSuccess(bootcampPersonSaved -> log.debug("Bootcamp-Person saved: {}",
            bootcampPersonSaved
        ));
  }

}

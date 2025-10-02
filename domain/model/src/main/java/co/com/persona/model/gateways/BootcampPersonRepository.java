package co.com.persona.model.gateways;

import co.com.persona.model.bootcamp.BootcampPerson;
import reactor.core.publisher.Mono;

public interface BootcampPersonRepository {

  Mono<BootcampPerson> save(BootcampPerson data);

  Mono<Long> countByIdBootcamp(String idBootcamp);
}

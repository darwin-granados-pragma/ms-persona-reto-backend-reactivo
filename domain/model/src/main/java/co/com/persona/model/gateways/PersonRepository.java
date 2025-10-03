package co.com.persona.model.gateways;

import co.com.persona.model.person.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

  Mono<Person> save(Person person);

  Mono<Person> findByEmail(String email);

  Flux<Person> findByIdBootcamp(String idBootcamp);
}

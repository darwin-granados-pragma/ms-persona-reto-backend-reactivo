package co.com.persona.model.gateways;

import co.com.persona.model.person.Person;
import reactor.core.publisher.Mono;

public interface PersonRepository {
  Mono<Person> save(Person person);
}

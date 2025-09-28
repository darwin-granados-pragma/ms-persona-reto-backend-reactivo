package co.com.persona.usecase.person;

import co.com.persona.model.error.ErrorCode;
import co.com.persona.model.exception.ObjectNotFoundException;
import co.com.persona.model.gateways.PersonRepository;
import co.com.persona.model.gateways.TransactionalGateway;
import co.com.persona.model.person.Person;
import co.com.persona.model.person.PersonCreate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PersonUseCase {

  private final PersonRepository repository;
  private final TransactionalGateway transactionalGateway;

  public Mono<Person> createPerson(PersonCreate data) {
    return Mono
        .fromCallable(() -> Person
            .builder()
            .id(UUID
                .randomUUID()
                .toString())
            .name(data.getName())
            .email(data.getEmail())
            .birthDate(data.getBirthDate())
            .isNew(true)
            .build())
        .flatMap(repository::save)
        .as(transactionalGateway::execute);
  }

  public Mono<Person> findByEmail(String email) {
    return repository
        .findByEmail(email)
        .switchIfEmpty(Mono.error(new ObjectNotFoundException(ErrorCode.PERSON_NOT_FOUND, email)));
  }
}

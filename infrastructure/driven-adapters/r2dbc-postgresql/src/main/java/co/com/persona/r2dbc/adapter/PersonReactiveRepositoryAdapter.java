package co.com.persona.r2dbc.adapter;

import co.com.persona.model.error.ErrorCode;
import co.com.persona.model.exception.ConstraintException;
import co.com.persona.model.gateways.PersonRepository;
import co.com.persona.model.person.Person;
import co.com.persona.r2dbc.entity.PersonEntity;
import co.com.persona.r2dbc.helper.ReactiveAdapterOperations;
import co.com.persona.r2dbc.repository.PersonReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class PersonReactiveRepositoryAdapter extends
    ReactiveAdapterOperations<Person, PersonEntity, String, PersonReactiveRepository> implements
    PersonRepository {

  public PersonReactiveRepositoryAdapter(PersonReactiveRepository repository, ObjectMapper mapper) {
    super(repository, mapper, d -> mapper.map(d, Person.class));
  }

  @Override
  public Mono<Person> save(Person person) {
    log.info("Saving person with name: {}", person.getName());
    return super
        .save(person)
        .onErrorMap(DataIntegrityViolationException.class, e -> {
              log.error("Data integrity violation: {}", e.getMessage());
              String message = e.getMessage();
              if (message.contains("fecha_nacimiento_unique_constraint")) {
                return new ConstraintException(ErrorCode.PERSON_EMAIL_ALREADY_EXISTS);
              }
              return new ConstraintException(ErrorCode.CONSTRAINT_VIOLATION);
            }
        )
        .doOnSuccess(personSaved -> log.debug("Person saved: {}", personSaved));
  }

  @Override
  public Mono<Person> findByEmail(String email) {
    log.info("Retrieving person details by email: {}", email);
    return super.repository
        .findByEmail(email)
        .map(this::toEntity)
        .doOnSuccess(person -> log.debug("Person retrieved: {}", person));
  }
}

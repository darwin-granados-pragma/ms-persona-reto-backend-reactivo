package co.com.persona.usecase.person;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import co.com.persona.model.gateways.PersonRepository;
import co.com.persona.model.gateways.TransactionalGateway;
import co.com.persona.model.person.Person;
import co.com.persona.model.person.PersonCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PersonUseCaseTest {

  private PersonCreate personCreate;
  private Person person;

  @Mock
  private PersonRepository repository;
  @Mock
  private TransactionalGateway transactionalGateway;
  @InjectMocks
  private PersonUseCase useCase;

  @BeforeEach
  void setUp() {
    personCreate = PersonCreate
        .builder()
        .name("test name")
        .email("Test email")
        .build();
    person = Person
        .builder()
        .id("Test id")
        .name(personCreate.getName())
        .email(personCreate.getEmail())
        .build();
  }

  @Test
  void shouldReturnCreatedPerson() {
    // Arrange
    when(repository.save(any(Person.class))).thenReturn(Mono.just(person));
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var result = useCase.createPerson(personCreate);

    // Assert
    StepVerifier
        .create(result)
        .expectNextMatches(personCreated -> personCreated
            .getId()
            .equals(person.getId()) && personCreated
            .getName()
            .equals(person.getName()) && personCreated
            .getEmail()
            .equals(person.getEmail()))
        .verifyComplete();
  }
}
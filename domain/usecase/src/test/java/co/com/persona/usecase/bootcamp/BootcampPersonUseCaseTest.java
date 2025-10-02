package co.com.persona.usecase.bootcamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.com.persona.model.bootcamp.Bootcamp;
import co.com.persona.model.bootcamp.BootcampPerson;
import co.com.persona.model.bootcamp.BootcampPersonCreate;
import co.com.persona.model.exception.BusinessException;
import co.com.persona.model.gateways.BootcampGateway;
import co.com.persona.model.gateways.BootcampPersonRepository;
import co.com.persona.model.gateways.TransactionalGateway;
import co.com.persona.model.person.Person;
import co.com.persona.usecase.person.PersonUseCase;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BootcampPersonUseCaseTest {

  @Mock
  private PersonUseCase personUseCase;

  @Mock
  private BootcampPersonRepository repository;

  @Mock
  private BootcampGateway bootcampGateway;

  @Mock
  private TransactionalGateway transactionalGateway;

  @InjectMocks
  private BootcampPersonUseCase bootcampPersonUseCase;

  private Person person;
  private Set<String> bootcamps;
  private BootcampPersonCreate createData;

  private Bootcamp bootcamp1;
  private Bootcamp bootcamp2;

  private static Stream<Set<String>> provideInvalidBootcampSizes() {
    return Stream.of(Collections.emptySet(), java.util.Set.of("b1", "b2", "b3", "b4", "b5"));
  }

  @BeforeEach
  void setUp() {
    String idPerson = "id Person";
    bootcamps = Set.of("test id bootcamp1", "test id bootcamp2");
    createData = new BootcampPersonCreate("test email", bootcamps);
    person = Person
        .builder()
        .id(idPerson)
        .email(createData.email())
        .build();
    bootcamp1 = Bootcamp
        .builder()
        .id("test id bootcamp1")
        .releaseDate(LocalDate.of(2025, 10, 1))
        .duration(8)
        .build();
    bootcamp2 = Bootcamp
        .builder()
        .id("test id bootcamp2")
        .releaseDate(LocalDate.of(2027, 10, 20))
        .duration(6)
        .build();
  }

  @Test
  void shouldAssignBootcampsToPersonSuccessfully() {
    // Arrange
    when(personUseCase.findByEmail(createData.email())).thenReturn(Mono.just(person));
    when(bootcampGateway.getBootcampsDetailsByIds(bootcamps)).thenReturn(Flux.just(bootcamp1,
        bootcamp2
    ));
    when(repository.save(any(BootcampPerson.class))).thenReturn(Mono.just(new BootcampPerson()));
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var result = bootcampPersonUseCase.assignBootcampsToPerson(createData);

    // Assert
    StepVerifier
        .create(result)
        .verifyComplete();
    verify(repository, times(2)).save(any(BootcampPerson.class));
    verify(bootcampGateway, times(1)).getBootcampsDetailsByIds(bootcamps);
  }

  @Test
  void shouldAssignBootcampsToPersonSuccessfully_WhenBootcampsSizeIsOne() {
    // Arrange
    when(personUseCase.findByEmail(createData.email())).thenReturn(Mono.just(person));
    when(bootcampGateway.getBootcampsDetailsByIds(bootcamps)).thenReturn(Flux.just(bootcamp1));
    when(repository.save(any(BootcampPerson.class))).thenReturn(Mono.just(new BootcampPerson()));
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var result = bootcampPersonUseCase.assignBootcampsToPerson(createData);

    // Assert
    StepVerifier
        .create(result)
        .verifyComplete();
    verify(repository, times(2)).save(any(BootcampPerson.class));
    verify(bootcampGateway, times(1)).getBootcampsDetailsByIds(bootcamps);
  }

  @Test
  void shouldThrowBusinessException_WhenScheduleConflictExist() {
    // Arrange
    bootcamp2.setReleaseDate(LocalDate.of(2025, 10, 20));
    when(personUseCase.findByEmail(createData.email())).thenReturn(Mono.just(person));
    when(bootcampGateway.getBootcampsDetailsByIds(bootcamps)).thenReturn(Flux.just(bootcamp1,
        bootcamp2
    ));
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var result = bootcampPersonUseCase.assignBootcampsToPerson(createData);

    // Assert
    StepVerifier
        .create(result)
        .expectError(BusinessException.class)
        .verify();

    verify(repository, never()).save(any());
  }

  @ParameterizedTest
  @MethodSource("provideInvalidBootcampSizes")
  void shouldReturnErrorWhenCapacitySizeIsInvalid(Set<String> invalidBootcampIds) {
    // Arrange
    when(personUseCase.findByEmail(createData.email())).thenReturn(Mono.just(person));
    var bootcampPersonCreate = new BootcampPersonCreate(createData.email(), invalidBootcampIds);
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var resultMono = bootcampPersonUseCase.assignBootcampsToPerson(bootcampPersonCreate);

    // Assert
    StepVerifier
        .create(resultMono)
        .expectError(BusinessException.class)
        .verify();
    verify(repository, never()).save(any());
  }

  @Test
  void shouldGetTotalPeopleByIdBootcamp() {
    // Arrange
    String idBootcamp = "test-id-bootcamp";
    long expectedCount = 5L;
    when(repository.countByIdBootcamp(idBootcamp)).thenReturn(Mono.just(expectedCount));

    // Act
    var result = bootcampPersonUseCase.getTotalPeopleByIdBootcamp(idBootcamp);

    // Assert
    StepVerifier.create(result)
        .expectNext(expectedCount)
        .verifyComplete();

    verify(repository, times(1)).countByIdBootcamp(idBootcamp);
  }
}
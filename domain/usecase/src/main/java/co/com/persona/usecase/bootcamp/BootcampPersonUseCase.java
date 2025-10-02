package co.com.persona.usecase.bootcamp;

import co.com.persona.model.bootcamp.Bootcamp;
import co.com.persona.model.bootcamp.BootcampPerson;
import co.com.persona.model.bootcamp.BootcampPersonCreate;
import co.com.persona.model.error.ErrorCode;
import co.com.persona.model.exception.BusinessException;
import co.com.persona.model.gateways.BootcampGateway;
import co.com.persona.model.gateways.BootcampPersonRepository;
import co.com.persona.model.gateways.TransactionalGateway;
import co.com.persona.usecase.person.PersonUseCase;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampPersonUseCase {

  private final PersonUseCase personUseCase;
  private final BootcampPersonRepository repository;
  private final BootcampGateway bootcampGateway;
  private final TransactionalGateway transactionalGateway;

  public Mono<Void> assignBootcampsToPerson(BootcampPersonCreate data) {
    return personUseCase
        .findByEmail(data.email())
        .flatMap(person -> {
          Set<String> bootcamps = data.idsBootcamps();
          return validateBootcampsSize(bootcamps)
              .then(validateReleaseAndDuration(bootcamps))
              .then(buildAndSave(bootcamps, person.getId()))
              .as(transactionalGateway::execute);
        });
  }

  public Mono<Long> getTotalPeopleByIdBootcamp(String idBootcamp) {
    return repository.countByIdBootcamp(idBootcamp);
  }

  private Mono<Void> buildAndSave(Set<String> idsBootcamps, String idPerson) {
    return Flux
        .fromIterable(idsBootcamps)
        .map(idBootcamp -> BootcampPerson
            .builder()
            .id(UUID
                .randomUUID()
                .toString())
            .idBootcamp(idBootcamp)
            .idPerson(idPerson)
            .isNew(true)
            .build())
        .flatMap(repository::save)
        .then();
  }

  private Mono<Void> validateBootcampsSize(Set<String> idsBootcamps) {
    if (idsBootcamps.isEmpty() || idsBootcamps.size() > 4) {
      return Mono.error(new BusinessException(ErrorCode.PERSON_BOOTCAMP_SIZE));
    }
    return Mono.empty();
  }

  private Mono<Void> validateReleaseAndDuration(Set<String> idsBootcamps) {
    return Mono.defer(() -> bootcampGateway
        .getBootcampsDetailsByIds(idsBootcamps)
        .collectList()
        .flatMap(bootcamps -> {
          if (bootcamps.size() < 2) {
            return Mono.empty();
          }
          bootcamps.sort(Comparator.comparing(Bootcamp::getReleaseDate));
          for (int i = 0; i < bootcamps.size() - 1; i++) {
            Bootcamp current = bootcamps.get(i);
            Bootcamp next = bootcamps.get(i + 1);
            LocalDate currentEndDate = current
                .getReleaseDate()
                .plusWeeks(current.getDuration());
            if (currentEndDate.isAfter(next.getReleaseDate())) {
              return Mono.error(new BusinessException(ErrorCode.BOOTCAMP_SCHEDULE_CONFLICT,
                  List.of(current.getId(), next.getId())
              ));
            }
          }
          return Mono.empty();
        }));
  }
}

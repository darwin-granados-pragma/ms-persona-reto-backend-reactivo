package co.com.persona.api.handler;

import co.com.persona.api.mapper.PersonRestMapper;
import co.com.persona.api.model.request.PersonCreateRequest;
import co.com.persona.model.person.PersonCreate;
import co.com.persona.usecase.person.PersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonHandler {

  private final PersonUseCase useCase;
  private final PersonRestMapper mapper;
  private final RequestValidator requestValidator;

  public Mono<ServerResponse> createPerson(ServerRequest serverRequest) {
    log.info("Received request to create a person at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return serverRequest
        .bodyToMono(PersonCreateRequest.class)
        .flatMap(request -> requestValidator
            .validate(request)
            .then(Mono.defer(() -> {
              PersonCreate personCreate = mapper.toPersonCreate(request);
              return useCase
                  .createPerson(personCreate)
                  .map(mapper::toResponse)
                  .flatMap(response -> ServerResponse
                      .status(HttpStatus.CREATED)
                      .contentType(MediaType.APPLICATION_JSON)
                      .bodyValue(response));
            })));
  }
}

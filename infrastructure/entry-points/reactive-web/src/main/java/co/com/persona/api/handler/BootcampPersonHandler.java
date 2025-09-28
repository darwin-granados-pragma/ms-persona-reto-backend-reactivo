package co.com.persona.api.handler;

import co.com.persona.api.mapper.BootcampPersonRestMapper;
import co.com.persona.api.model.request.BootcampPersonCreateRequest;
import co.com.persona.model.bootcamp.BootcampPersonCreate;
import co.com.persona.usecase.bootcamp.BootcampPersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootcampPersonHandler {

  private final BootcampPersonUseCase useCase;
  private final BootcampPersonRestMapper mapper;
  private final RequestValidator requestValidator;

  public Mono<ServerResponse> assignBootcampsToPerson(ServerRequest serverRequest) {
    log.info("Received request to assign bootcamps to a person at path={} method={}",
        serverRequest.path(),
        serverRequest.method()
    );
    return serverRequest
        .bodyToMono(BootcampPersonCreateRequest.class)
        .flatMap(request -> requestValidator
            .validate(request)
            .then(Mono.defer(() -> {
              BootcampPersonCreate dataToSave = mapper.toBootcampPersonCreate(request);
              return useCase
                  .assignBootcampsToPerson(dataToSave)
                  .then(ServerResponse
                      .noContent()
                      .build());
            })));
  }
}

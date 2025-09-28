package co.com.persona.consumer;

import co.com.persona.consumer.mapper.BootcampMapper;
import co.com.persona.consumer.model.BootcampRestResponse;
import co.com.persona.model.bootcamp.Bootcamp;
import co.com.persona.model.error.ErrorCode;
import co.com.persona.model.exception.InvalidBootcampException;
import co.com.persona.model.gateways.BootcampGateway;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements BootcampGateway {

  private final WebClient client;
  private final BootcampMapper bootcampMapper;

  @Override
  public Flux<Bootcamp> getBootcampsDetailsByIds(Set<String> bootcamps) {
    return client
        .post()
        .uri("/api/v1/bootcamp/all")
        .bodyValue(bootcamps)
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            clientResponse -> clientResponse
                .bodyToMono(ErrorResponse.class)
                .flatMap(errorBody -> Mono.error(new InvalidBootcampException(ErrorCode.INVALID_BOOTCAMP,
                    errorBody.error()
                )))
        )
        .bodyToFlux(BootcampRestResponse.class)
        .map(bootcampMapper::toDomain);

  }
}

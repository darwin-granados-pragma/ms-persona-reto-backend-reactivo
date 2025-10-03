package co.com.persona.consumer.rest;

import co.com.persona.model.gateways.ReportGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReportRestConsumer implements ReportGateway {

  private final WebClient client;

  public ReportRestConsumer(@Qualifier("reportWebClient") WebClient client) {
    this.client = client;
  }

  @Override
  public Mono<Void> upgradeTotalPeople(String idBootcamp) {
    log.info("Upgrading total people for bootcamp id={}", idBootcamp);
    return client
        .post()
        .uri("/api/v1/reporte/{idBootcamp}/personas", idBootcamp)
        .retrieve()
        .bodyToMono(Void.class)
        .onErrorResume(WebClientResponseException.class, ex -> {
              log.error("Error occurred while upgrading total people for bootcamp id={}: {}",
                  idBootcamp,
                  ex.getMessage()
              );
              return Mono.empty();
            }
        );
  }
}

package co.com.persona.model.gateways;

import reactor.core.publisher.Mono;

public interface ReportGateway {

  Mono<Void> upgradeTotalPeople(String idBootcamp);
}

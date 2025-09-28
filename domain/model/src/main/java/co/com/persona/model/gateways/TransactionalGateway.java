package co.com.persona.model.gateways;

import reactor.core.publisher.Mono;

public interface TransactionalGateway {

  <T> Mono<T> execute(Mono<T> action);
}

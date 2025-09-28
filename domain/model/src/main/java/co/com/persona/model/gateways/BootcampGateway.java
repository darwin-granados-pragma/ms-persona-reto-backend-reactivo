package co.com.persona.model.gateways;

import co.com.persona.model.bootcamp.Bootcamp;
import java.util.Set;
import reactor.core.publisher.Flux;

public interface BootcampGateway {

  Flux<Bootcamp> getBootcampsDetailsByIds(Set<String> bootcamps);
}

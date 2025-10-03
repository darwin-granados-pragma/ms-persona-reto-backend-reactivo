package co.com.persona.consumer.config;

import static co.com.persona.consumer.config.HttpConnector.getClientHttpConnector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BootcampRestConsumerConfig {

  private final String url;

  private final int timeout;

  public BootcampRestConsumerConfig(@Value("${adapter.bootcamp.restconsumer.url}") String url,
      @Value("${adapter.bootcamp.restconsumer.timeout}") int timeout) {
    this.url = url;
    this.timeout = timeout;
  }

  @Bean("bootcampWebClient")
  public WebClient getBootcampWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .clientConnector(getClientHttpConnector(timeout))
        .build();
  }

}

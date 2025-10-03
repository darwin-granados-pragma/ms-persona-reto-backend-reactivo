package co.com.persona.consumer.config;

import static co.com.persona.consumer.config.HttpConnector.getClientHttpConnector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReportRestConsumerConfig {

  private final String url;

  private final int timeout;

  public ReportRestConsumerConfig(@Value("${adapter.report.restconsumer.url}") String url,
      @Value("${adapter.report.restconsumer.timeout}") int timeout) {
    this.url = url;
    this.timeout = timeout;
  }

  @Bean("reportWebClient")
  public WebClient getReportWebClient(WebClient.Builder builder) {
    return builder
        .baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .clientConnector(getClientHttpConnector(timeout))
        .build();
  }


}

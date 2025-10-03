package co.com.persona.consumer.config;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

public class HttpConnector {

  private HttpConnector() {
  }

  public static ClientHttpConnector getClientHttpConnector(int timeout) {
        /*
        IF YO REQUIRE APPEND SSL CERTIFICATE SELF SIGNED: this should be in the default cacerts trustore
        */
    return new ReactorClientHttpConnector(HttpClient
        .create()
        .compress(true)
        .keepAlive(true)
        .option(CONNECT_TIMEOUT_MILLIS, timeout)
        .doOnConnected(connection -> {
          connection.addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS));
          connection.addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS));
        }));
  }
}

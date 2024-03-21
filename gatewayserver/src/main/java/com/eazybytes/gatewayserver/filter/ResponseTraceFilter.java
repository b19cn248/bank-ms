package com.eazybytes.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {
  private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

  private final FilterUtility filterUtility;

  public ResponseTraceFilter(FilterUtility filterUtility) {
    this.filterUtility = filterUtility;
  }

  @Bean
  public GlobalFilter postGlobalFilter() {
    return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
      HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

      String correlationID = filterUtility.getCorrelationId(requestHeaders);
      logger.debug("Eazybank-correlation-id found in tracking filter: {}. ", correlationID);

      exchange.getResponse().getHeaders().add("Eazybank-correlation-id", correlationID);
    }));
  }
}

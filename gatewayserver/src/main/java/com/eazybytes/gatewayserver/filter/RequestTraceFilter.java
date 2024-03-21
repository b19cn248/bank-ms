package com.eazybytes.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

  private final FilterUtility filterUtility;

  public RequestTraceFilter(FilterUtility filterUtility) {
    this.filterUtility = filterUtility;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
    if (this.isCorrelationIdPresent(requestHeaders)) {
      if (logger.isDebugEnabled()) {
        logger.debug("Eazybank-correlation-id found in tracking filter: {}. ", filterUtility.getCorrelationId(requestHeaders));
      }
    } else {
      String correlationID = UUID.randomUUID().toString();
      exchange = filterUtility.setCorrelationId(exchange, correlationID);
      if (logger.isDebugEnabled()) {
        logger.debug("Eazybank-correlation-id generated in tracking filter: {}.", correlationID);
      }
    }

    return chain.filter(exchange);
  }


  private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
    return filterUtility.getCorrelationId(requestHeaders) != null;
  }
}

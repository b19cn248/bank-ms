package com.eazybytes.gatewayserver.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Component
public class FilterUtility {

  public static final String CORRELATION_ID = "eazybank-correlation-id";

  public String getCorrelationId(HttpHeaders requestHeaders) {
    List<String> values = requestHeaders.get(CORRELATION_ID);
    if (values != null && !values.isEmpty()) {
      return values.get(0);
    } else {
      return null;
    }
  }


  public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
    return exchange.mutate().request(
          exchange.getRequest().mutate().header(name, value).build()
    ).build();
  }

  public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
  }
}

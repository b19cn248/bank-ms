package com.eazybytes.gatewayserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Component
@Configuration
public class OpenAPIConfiguration {

  @Bean
  public OpenAPI gateWayOpenApi() {
    return new OpenAPI().info(new Info().title("Demo Application Microservices APIs ")
          .description("Documentation for all the Microservices in Demo Application")
          .version("v1.0.0")
          .contact(new Contact()
                .name("Demo Application Development Team")
                .email("demo_support@imaginarycompany.com")));
  }

  @Bean
  public CorsWebFilter corsWebFilter() {
    final CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:7080"));
    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "HEAD", "PUT"));
    corsConfig.addAllowedHeader("Access-Control-Allow-Origin");

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}

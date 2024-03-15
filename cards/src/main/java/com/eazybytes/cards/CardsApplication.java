package com.eazybytes.cards;

import com.eazybytes.cards.dto.CardsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
      info = @Info(
            title = "Cards microservice REST API Documentation",
            description = "EazyBank Cards microservice REST API Documentation",
            version = "v1",
            contact = @Contact(
                  name = "Hieu PTIT",
                  email = "tutor@eazybytes.com",
                  url = "https://www.eazybytes.com"
            ),
            license = @License(
                  name = "Apache 2.0",
                  url = "https://www.eazybytes.com"
            )
      ),
      externalDocs = @ExternalDocumentation(
            description = "EazyBank Cards microservice REST API Documentation",
            url = "https://www.eazybytes.com/swagger-ui.html"
      )
)
@EnableConfigurationProperties({CardsContactInfoDTO.class})
public class CardsApplication {

  public static void main(String[] args) {
    SpringApplication.run(CardsApplication.class, args);
  }

}
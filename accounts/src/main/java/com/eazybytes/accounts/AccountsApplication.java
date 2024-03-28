package com.eazybytes.accounts;

import com.eazybytes.accounts.controller.AppProperties;
import com.eazybytes.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
      servers = {
            @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8072", description = "Local Server"),
      },
      info = @Info(
            title = "Accounts microservice REST API Documentation",
            description = "EazyBank Accounts microservice REST API Documentation",
            version = "v1",
            contact = @Contact(
                  name = "Hieu PTIT",
                  email = "hieunm123.ptit@gmail.com",
                  url = "https://www.eazybytes.com"
            ),
            license = @License(
                  name = "Apache 2.0",
                  url = "https://www.eazybytes.com"
            )
      ),
      externalDocs = @ExternalDocumentation(
            description = "EazyBank Accounts microservice REST API Documentation",
            url = "https://www.eazybytes.com/swagger-ui.html"
      )
)
@EnableConfigurationProperties({AppProperties.class, AccountsContactInfoDTO.class})
@EnableDiscoveryClient
@EnableFeignClients
public class AccountsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

}

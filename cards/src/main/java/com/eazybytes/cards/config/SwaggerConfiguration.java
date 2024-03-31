package com.eazybytes.cards.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  private static final String OAUTH2_SCHEME_NAME = "OAuth2ClientCredentials";
  private static final String TOKEN_URL = "http://localhost:7080/realms/master/protocol/openid-connect/token"; // Replace with your token URL

  private SecurityScheme oauthClientCredentialsScheme() {
    OAuthFlows oauthFlows = new OAuthFlows();
    OAuthFlow clientCredentialsFlow = new OAuthFlow();
    clientCredentialsFlow.setTokenUrl(TOKEN_URL);
    oauthFlows.setClientCredentials(clientCredentialsFlow);

    return new SecurityScheme()
          .type(SecurityScheme.Type.OAUTH2)
          .flows(oauthFlows)
          .name(OAUTH2_SCHEME_NAME);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
          .addSecurityItem(new SecurityRequirement().addList(OAUTH2_SCHEME_NAME))
          .components(new Components().addSecuritySchemes(OAUTH2_SCHEME_NAME, oauthClientCredentialsScheme()))
          .info(new Info().title("TITLE").description("DESCRIPTION"));
  }
}

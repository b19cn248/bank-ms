package com.eazybytes.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsContactInfoDTO {
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}

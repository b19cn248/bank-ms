package com.eazybytes.accounts.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(
      name = "Response",
      description = "Schema to hold Successfully response information")
public class ResponseDTO {

  @Schema(
        description = "Status code representing the response",
        example = "200"
  )
  private String statusCode;

  @Schema(
        description = "Status message representing the response",
        example = "Request processed successfully"
  )
  private String statusMsg;
}

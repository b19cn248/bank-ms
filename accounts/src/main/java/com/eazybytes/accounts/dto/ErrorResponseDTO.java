package com.eazybytes.accounts.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(
      name = "ErrorResponse",
      description = "Schema to hold error response information"
)
public class ErrorResponseDTO {
  @Schema(
        description = "API path invoked by client",
        example = "/accounts/1"
  )
  private String apiPath;

  @Schema(
        description = "Error code representing the error happened",
        example = "404"
  )
  private HttpStatus errorCode;

  @Schema(
        description = "Error message representing the error happened",
        example = "Account not found"
  )
  private String errorMessage;

  @Schema(
        description = "Time representing when the error happened",
        example = "2021-08-01T12:00:00"
  )
  private LocalDateTime errorTime;
}

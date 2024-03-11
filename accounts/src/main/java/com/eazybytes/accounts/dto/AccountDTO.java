package com.eazybytes.accounts.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
      name = "Account",
      description = "Schema to hold Account information")
public class AccountDTO {

  @Schema(
        description = "Unique identifier of the customer",
        example = "1489897130"
  )
  @NotEmpty(message = "Account number cannot be empty")
  @Pattern(regexp = "(^$|\\d{10})", message = "Account number must be 10 digits")
  private Long accountNumber;

  @Schema(
        description = "Type of the account",
        example = "Savings"
  )
  @NotEmpty(message = "Account type cannot be empty")
  private String accountType;

  @Schema(
        description = "Branch address of the account",
        example = "123, Main Street, New York, USA"
  )
  @NotEmpty(message = "Branch address cannot be empty")
  private String branchAddress;
}

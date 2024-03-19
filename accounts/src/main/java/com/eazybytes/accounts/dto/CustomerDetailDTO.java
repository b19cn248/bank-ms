package com.eazybytes.accounts.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(name = "CustomerDetail", description = "Schema to hold Customer nad Account Information")
public class CustomerDetailDTO {

  @Schema(
        description = "Unique identifier of the customer",
        example = "HieuNguyen"
  )
  @NotNull(message = "Name cannot be null")
  @Length(min = 5, max = 30, message = "Name should have atleast 2 characters")
  private String name;


  @Schema(
        description = "Email of the customer",
        example = "hieunm123.ptit@gmail.com")
  @Email(message = "Email should be valid")
  private String email;

  @Schema(
        description = "Mobile number of the customer",
        example = "0987975814")
  @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
  private String mobileNumber;

  @Schema(description = "Account details of the customer")
  private AccountDTO accountDTO;

  @Schema(description = "Loan details of the customer")
  private LoanDTO loanDTO;

  @Schema(description = "Card details of the customer")

  private CardDTO cardDTO;
}

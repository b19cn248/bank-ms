package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
      name = "RESET API for Customers in EazyBank",
      description = "REST API for Customer operations in EazyBank"
)
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerService customerService;

  @Operation(
        summary = "Fetch Customer Details REST API",
        description = "REST API to fetch existing Customer details inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
              ),
              @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @GetMapping
  public ResponseEntity<CustomerDetailDTO> fetchCustomerDetails(
        @RequestHeader("eazybank-correlation-id") String correlationId,
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    log.debug("Fetching customer details for the correlationId:{}, mobile number: {}", correlationId, mobileNumber);
    return ResponseEntity.ok(customerService.getCustomerDetails(mobileNumber, correlationId));
  }
}

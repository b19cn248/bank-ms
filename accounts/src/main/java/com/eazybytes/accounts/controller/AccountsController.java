package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.AccountsContactInfoDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.eazybytes.accounts.constant.AccountsConstant.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@Tag(
      name = "CRUD REST APIs for Accounts in EazyBank",
      description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@Slf4j
public class AccountsController {

  private final IAccountService accountService;

  private final AccountsContactInfoDTO accountsContactInfoDto;

  @Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer &  Account inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
              )
        }
  )

  @PostMapping
  public ResponseEntity<ResponseDTO> createAccount(@RequestBody @Valid CustomerDTO customerDTO) {

    accountService.createAccount(customerDTO);

    return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(new ResponseDTO(STATUS_201, MESSAGE_201));
  }

  @Operation(
        summary = "Fetch Account REST API",
        description = "REST API to fetch existing Customer & Account inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
              ),
              @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @GetMapping
  public ResponseEntity<CustomerDTO> fetchCustomerDetails(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    return ResponseEntity
          .status(HttpStatus.OK)
          .body(accountService.fetchCustomerDetails(mobileNumber));
  }

  @Operation(
        summary = "Update Account REST API",
        description = "REST API to update existing Customer & Account inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
              ),
              @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @PutMapping
  public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDto) {
    boolean isUpdated = accountService.updateAccount(customerDto);
    if (isUpdated) {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseDTO(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseDTO(STATUS_500, MESSAGE_500));
    }
  }

  @Operation(
        summary = "Delete Account REST API",
        description = "REST API to delete existing Customer & Account inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
              ),
              @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @DeleteMapping
  public ResponseEntity<ResponseDTO> deleteAccountDetails(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits")
        String mobileNumber
  ) {
    boolean isDeleted = accountService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseDTO(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseDTO(STATUS_500, MESSAGE_500));
    }
  }

  @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() throws TimeoutException {

    log.debug("Build Info details");

    throw new RuntimeException("This is a test exception");

//    return ResponseEntity
//          .status(HttpStatus.OK)
//          .body(buildVersion);
  }

  public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {

    log.debug("Fallback method for Build Info details");

    return ResponseEntity
          .status(HttpStatus.OK)
          .body("0.9");
  }

  @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
  )
  @GetMapping("/contact-info")
  @RateLimiter(name = "getPropertiesByConfigurationPropertiesAnnotation", fallbackMethod = "fallbackMethod")
  public ResponseEntity<AccountsContactInfoDTO> getPropertiesByConfigurationPropertiesAnnotation() {

    log.info("Contact Info details that can be reached out in case of any issues");

    return ResponseEntity
          .status(HttpStatus.OK)
          .body(accountsContactInfoDto);
  }

  public ResponseEntity<AccountsContactInfoDTO> fallbackMethod(Throwable throwable) {

    log.info("Fallback method for Contact Info details");

    Map<String, String> contactDetails = new HashMap<>();
    contactDetails.put("name", "Hieu PC - Product Owner with docker MYSQL");
    contactDetails.put("email", "hieunm123.ptit@gmail.com");

    return ResponseEntity
          .status(HttpStatus.OK)
          .body(AccountsContactInfoDTO.builder()
                .message("Welcome to EazyBank accounts related local APIs Production with FALLBACK")
                .contactDetails(contactDetails)
                .onCallSupport(List.of("0987975814", "0973716456"))
                .build());
  }
}

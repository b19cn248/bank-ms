package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eazybytes.accounts.constant.AccountsConstant.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@Tag(
      name = "CRUD REST APIs for Accounts in EazyBank",
      description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
public class AccountsController {

  private final IAccountService accountService;

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

}

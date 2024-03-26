package com.eazybytes.loans.controller;

import com.eazybytes.loans.dto.ErrorResponseDTO;
import com.eazybytes.loans.dto.LoanDTO;
import com.eazybytes.loans.dto.LoansContactInfoDto;
import com.eazybytes.loans.dto.ResponseDTO;
import com.eazybytes.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eazybytes.loans.constants.LoansConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
@Slf4j
public class LoanController {

  private final LoanService loanService;
  private final LoansContactInfoDto loansContactInfoDto;


  @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create new Loan inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                          schema = @Schema(implementation = ResponseDTO.class)
                    )
              )
        }
  )
  @PostMapping
  public ResponseEntity<ResponseDTO> createLoan(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    loanService.createLoan(mobileNumber);
    return ResponseEntity.ok(new ResponseDTO(
          STATUS_201,
          MESSAGE_201
    ));
  }


  @Operation(
        summary = "Fetch Loan REST API",
        description = "REST API to fetch existing Loan inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                          schema = @Schema(implementation = LoanDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status EXPECTATION_FAILED",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @GetMapping
  public ResponseEntity<LoanDTO> fetchLoan(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {

    log.info("Fetching loan for mobile number: {}", mobileNumber);

    return ResponseEntity
          .status(HttpStatus.OK)
          .body(loanService.fetchLoan(mobileNumber));
  }


  @Operation(
        summary = "Update Loan REST API",
        description = "REST API to update existing Loan inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                          schema = @Schema(implementation = ResponseDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status EXPECTATION_FAILED",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @PutMapping
  public ResponseEntity<ResponseDTO> updateLoan(
        @RequestBody LoanDTO loanDTO
  ) {
    boolean isUpdated = loanService.updateLoan(loanDTO);

    return ResponseEntity.ok(new ResponseDTO(
          isUpdated ? STATUS_200 : STATUS_417,
          isUpdated ? MESSAGE_200 : MESSAGE_417_DELETE
    ));
  }


  @Operation(
        summary = "Delete Loan REST API",
        description = "REST API to delete existing Loan inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                          schema = @Schema(implementation = ResponseDTO.class)
                    )
              ),
              @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status EXPECTATION_FAILED",
                    content = @Content(
                          schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
              )
        }
  )
  @DeleteMapping
  public ResponseEntity<ResponseDTO> deleteLoan(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    boolean isDeleted = loanService.deleteLoan(mobileNumber);

    return ResponseEntity.ok(new ResponseDTO(
          isDeleted ? STATUS_200 : STATUS_417,
          isDeleted ? MESSAGE_200 : MESSAGE_417_DELETE
    ));
  }

  @GetMapping("/contact-info")
  public ResponseEntity<LoansContactInfoDto> getContactInfo() {
    log.debug("Fetching contact info for loans");
    return ResponseEntity.ok(loansContactInfoDto);
  }
}

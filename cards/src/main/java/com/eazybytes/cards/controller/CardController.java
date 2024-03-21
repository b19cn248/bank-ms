package com.eazybytes.cards.controller;

import com.eazybytes.cards.dto.CardDTO;
import com.eazybytes.cards.dto.CardsContactInfoDTO;
import com.eazybytes.cards.dto.ErrorResponseDTO;
import com.eazybytes.cards.dto.ResponseDTO;
import com.eazybytes.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eazybytes.cards.constants.CardsConstants.*;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CardController {

  private final CardService cardService;

  private final CardsContactInfoDTO cardsContactInfoDTO;

  @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Card inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
              )
        }
  )
  @PostMapping
  public ResponseEntity<ResponseDTO> createCard(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    cardService.createCard(mobileNumber);
    return ResponseEntity.ok(new ResponseDTO(
          STATUS_201,
          MESSAGE_201
    ));
  }


  @Operation(
        summary = "Fetch Card REST API",
        description = "REST API to fetch existing Card inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                          schema = @Schema(implementation = CardDTO.class)
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
  public ResponseEntity<CardDTO> fetchCard(
        @RequestHeader("eazybank-correlation-id") String correlationId,
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {

    log.debug("Fetching card details for the correlationId:{}, mobile number: {}", correlationId, mobileNumber);

    return ResponseEntity
          .status(HttpStatus.OK)
          .body(cardService.fetchCard(mobileNumber));
  }


  @Operation(
        summary = "Update Card REST API",
        description = "REST API to update existing Card inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
  public ResponseEntity<ResponseDTO> updateCard(@RequestBody @Valid CardDTO cardDTO) {
    boolean isUpdated = cardService.updateCard(cardDTO);

    if (isUpdated) {
      return ResponseEntity.ok(new ResponseDTO(
            STATUS_200,
            MESSAGE_200
      ));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDTO(
                  STATUS_417,
                  MESSAGE_417_UPDATE
            ));
    }
  }

  @Operation(
        summary = "Delete Card REST API",
        description = "REST API to delete existing Card inside EazyBank",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
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
  public ResponseEntity<ResponseDTO> deleteCard(
        @RequestParam @Pattern(regexp = "(^$|\\d{10})", message = "Mobile number must be 10 digits") String mobileNumber
  ) {
    boolean isDeleted = cardService.deleteCard(mobileNumber);

    if (isDeleted) {
      return ResponseEntity.ok(new ResponseDTO(
            STATUS_200,
            MESSAGE_200
      ));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseDTO(
                  STATUS_417,
                  MESSAGE_417_DELETE
            ));
    }
  }


  @GetMapping("/contact-info")
  public ResponseEntity<CardsContactInfoDTO> getContactInfo() {
    return ResponseEntity.ok(cardsContactInfoDTO);
  }
}

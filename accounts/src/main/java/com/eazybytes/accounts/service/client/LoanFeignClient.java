package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans")
public interface LoanFeignClient {

  @GetMapping(value = "/api/v1/loans", consumes = "application/json")
  ResponseEntity<LoanDTO> fetchLoan(
        @RequestHeader("eazybank-correlation-id") String correlationId,
        @RequestParam String mobileNumber
  );
}

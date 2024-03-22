package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallback implements LoanFeignClient {
  @Override
  public ResponseEntity<LoanDTO> fetchLoan(String correlationId, String mobileNumber) {
    return null;
  }
}

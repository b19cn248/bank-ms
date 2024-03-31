package com.eazybytes.accounts.function;

import com.eazybytes.accounts.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class AccountFunctions {

  @Bean
  public Consumer<Long> updateCommunication(IAccountService iAccountService) {
    return accountNumber -> {
      log.info("Updating communication for account number: {}", accountNumber);
      iAccountService.updateCommunicationStatus(accountNumber);
    };
  }

}

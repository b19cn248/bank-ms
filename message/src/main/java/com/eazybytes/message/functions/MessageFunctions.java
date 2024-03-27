package com.eazybytes.message.functions;

import com.eazybytes.message.dto.AccountMsgDTO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class MessageFunctions {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(MessageFunctions.class);

  @Bean
  public Function<AccountMsgDTO, AccountMsgDTO> email() {
    return accountMsgDTO -> {
      log.info("Sending email to {}", accountMsgDTO.toString());
      return accountMsgDTO;
    };
  }

  @Bean
  public Function<AccountMsgDTO, Long> sms() {
    return accountMsgDTO -> {
      log.info("Sending sms to {}", accountMsgDTO.toString());
      return accountMsgDTO.accountNumber();
    };
  }

}

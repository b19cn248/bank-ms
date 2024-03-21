package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.CustomerDetailDTO;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.CustomerService;
import com.eazybytes.accounts.service.client.CardFeignClient;
import com.eazybytes.accounts.service.client.LoanFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eazybytes.accounts.constant.AccountsConstant.CUSTOMER;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final CardFeignClient cardFeignClient;
  private final LoanFeignClient loanFeignClient;

  @Override
  public CustomerDetailDTO getCustomerDetails(String mobileNumber, String correlationId) {
    log.info("Fetching customer details for mobile number: {}", mobileNumber);

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
          .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "mobile number", mobileNumber));

    Account account = accountRepository.findByCustomerId(customer.getId())
          .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getId().toString()));

    CustomerDetailDTO customerDetailDTO = CustomerMapper.mapToCustomerDetailDTO(customer, new CustomerDetailDTO());
    customerDetailDTO.setAccountDTO(AccountMapper.mapToAccountsDTO(account, new AccountDTO()));
    customerDetailDTO.setCardDTO(cardFeignClient.fetchCard(correlationId, mobileNumber).getBody());
    customerDetailDTO.setLoanDTO(loanFeignClient.fetchLoan(correlationId, mobileNumber).getBody());

    return customerDetailDTO;
  }
}

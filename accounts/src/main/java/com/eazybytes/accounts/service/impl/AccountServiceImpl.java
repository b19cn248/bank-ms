package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.AccountMsgDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.eazybytes.accounts.constant.AccountsConstant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements IAccountService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final StreamBridge streamBridge;
  private final Random random = new Random();

  @Override
  public void createAccount(CustomerDTO customerDTO) {
    log.info("Creating account for customer: {}", customerDTO);

    Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());

    Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

    if (existingCustomer.isPresent()) {
      throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
    }

    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy("Anonymous");

    Customer savedCustomer = customerRepository.save(customer);

    Account savedAccount = accountRepository.save(this.createNewAccount(savedCustomer));

    this.sendCommunication(savedAccount, savedCustomer);
  }

  @Override
  public CustomerDTO fetchCustomerDetails(String mobileNumber) {
    log.info("Fetching customer details for mobile number: {}", mobileNumber);

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
          .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "mobile number", mobileNumber));

    Account account = accountRepository.findByCustomerId(customer.getId())
          .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT, "customerId", customer.getId().toString()));

    CustomerDTO customerDto = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
    customerDto.setAccountDTO(AccountMapper.mapToAccountsDTO(account, new AccountDTO()));
    return customerDto;
  }

  @Override
  public boolean updateAccount(CustomerDTO customerDTO) {
    log.info("Updating account for customer: {}", customerDTO);

    boolean isUpdated = false;

    AccountDTO accountDTO = customerDTO.getAccountDTO();

    if (accountDTO != null) {
      Account account = accountRepository.findById(accountDTO.getAccountNumber())
            .orElseThrow(() -> new ResourceNotFoundException(
                  ACCOUNT,
                  "accountNumber",
                  accountDTO.getAccountNumber().toString())
            );

      AccountMapper.mapToAccounts(accountDTO, account);

      account = accountRepository.save(account);

      Long customerId = account.getCustomerId();
      Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "customerId", customerId.toString()));

      CustomerMapper.mapToCustomer(customerDTO, customer);

      customerRepository.save(customer);

      isUpdated = true;
    }
    return isUpdated;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    log.info("Deleting account for mobile number: {}", mobileNumber);

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
          .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "mobile number", mobileNumber));

    accountRepository.deleteByCustomerId(customer.getId());
    customerRepository.deleteById(customer.getId());

    return true;
  }

  @Override
  public boolean updateCommunicationStatus(Long accountNumber) {
    log.info("Updating communication status for account number: {}", accountNumber);

    boolean isUpdated = false;

    if (Objects.nonNull(accountNumber)) {
      Account account = accountRepository.findById(accountNumber)
            .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT, "accountNumber", accountNumber.toString()));

      account.setCommunicationSw(true);
      accountRepository.save(account);

      isUpdated = true;
    }

    return isUpdated;
  }

  private void sendCommunication(Account account, Customer customer) {
    var accountMsgDto = new AccountMsgDTO(
          account.getAccountNumber(),
          customer.getName(),
          customer.getEmail(),
          customer.getMobileNumber()
    );
    log.info("Sending message to the communication service: {}", accountMsgDto);
    var result = streamBridge.send("sendCommunication-out-0", accountMsgDto);
    log.info("Is the Communication Request sent successfully: {}", result);
  }

  private Account createNewAccount(Customer customer) {
    return Account.builder()
          .customerId(customer.getId())
          .accountNumber(1000000000L + random.nextInt(900000000))
          .accountType(SAVINGS)
          .branchAddress(ADDRESS)
          .build();
  }
}

package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoanDTO;
import com.eazybytes.loans.entity.Loan;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoanMapper;
import com.eazybytes.loans.repository.LoanRepository;
import com.eazybytes.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.eazybytes.loans.constants.LoansConstants.LOAN;
import static com.eazybytes.loans.constants.LoansConstants.MOBILE_NUMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

  private final LoanRepository repository;
  private final Random random = new Random();

  @Override
  public void createLoan(String mobileNumber) {
    log.info("Creating loan for mobile number: {}", mobileNumber);

    Optional<Loan> optionalLoan = repository.findByMobileNumber(mobileNumber);

    if (optionalLoan.isPresent()) {
      throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
    }

    repository.save(createNewLoan(mobileNumber));
  }

  @Override
  public LoanDTO fetchLoan(String mobileNumber) {
    log.info("Fetching loan for mobile number: {}", mobileNumber);

    Loan loan = repository.findByMobileNumber(mobileNumber).orElseThrow(
          () -> new ResourceNotFoundException(LOAN, MOBILE_NUMBER, mobileNumber)
    );

    return LoanMapper.toLoanDTO(loan, new LoanDTO());
  }

  @Override
  public boolean updateLoan(LoanDTO loanDTO) {

    log.info("Updating loan for mobile number: {}", loanDTO.getMobileNumber());

    Loan loan = repository.findByMobileNumber(loanDTO.getMobileNumber()).orElseThrow(
          () -> new ResourceNotFoundException(LOAN, MOBILE_NUMBER, loanDTO.getMobileNumber())
    );

    LoanMapper.toEntity(loanDTO, loan);
    repository.save(loan);

    return true;
  }

  @Override
  public boolean deleteLoan(String mobileNumber) {
    log.info("Deleting loan for mobile number: {}", mobileNumber);

    Loan loan = repository.findByMobileNumber(mobileNumber).orElseThrow(
          () -> new ResourceNotFoundException(LOAN, MOBILE_NUMBER, mobileNumber)
    );

    repository.deleteById(loan.getId());

    return true;
  }


  private Loan createNewLoan(String mobileNumber) {
    Loan newLoan = new Loan();
    long randomLoanNumber = 100000000000L + random.nextInt(900000000);
    newLoan.setLoanNumber(Long.toString(randomLoanNumber));
    newLoan.setMobileNumber(mobileNumber);
    newLoan.setLoanType(LoansConstants.HOME_LOAN);
    newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
    newLoan.setAmountPaid(0);
    newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
    return newLoan;
  }
}

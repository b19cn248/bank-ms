package com.eazybytes.loans.mapper;

import com.eazybytes.loans.dto.LoanDTO;
import com.eazybytes.loans.entity.Loan;

public class LoanMapper {

  private LoanMapper() {
  }

  public static LoanDTO toLoanDTO(Loan loan, LoanDTO loanDTO) {
    loanDTO.setMobileNumber(loan.getMobileNumber());
    loanDTO.setLoanNumber(loan.getLoanNumber());
    loanDTO.setLoanType(loan.getLoanType());
    loanDTO.setTotalLoan(loan.getTotalLoan());
    loanDTO.setAmountPaid(loan.getAmountPaid());
    loanDTO.setOutstandingAmount(loan.getOutstandingAmount());
    return loanDTO;
  }

  public static Loan toEntity(LoanDTO loanDTO, Loan loan) {
    loan.setMobileNumber(loanDTO.getMobileNumber());
    loan.setLoanNumber(loanDTO.getLoanNumber());
    loan.setLoanType(loanDTO.getLoanType());
    loan.setTotalLoan(loanDTO.getTotalLoan());
    loan.setAmountPaid(loanDTO.getAmountPaid());
    loan.setOutstandingAmount(loanDTO.getOutstandingAmount());
    return loan;
  }
}

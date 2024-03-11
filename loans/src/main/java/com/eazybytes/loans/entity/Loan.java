package com.eazybytes.loans.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String mobileNumber;

  private String loanNumber;

  private String loanType;

  private int totalLoan;

  private int amountPaid;

  private int outstandingAmount;
}

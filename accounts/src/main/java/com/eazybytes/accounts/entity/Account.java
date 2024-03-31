package com.eazybytes.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
@Builder
public class Account extends BaseEntity {

  private Long customerId;

  @Id
  private Long accountNumber;

  private String accountType;

  private String branchAddress;

  private Boolean communicationSw;
}

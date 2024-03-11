package com.eazybytes.cards.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
@Entity
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String mobileNumber;

  private String cardNumber;

  private String cardType;

  private int totalLimit;

  private int amountUsed;

  private int availableAmount;
}

package com.eazybytes.cards.repository;

import com.eazybytes.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

  Optional<Card> findByMobileNumber(String mobileNumber);
}

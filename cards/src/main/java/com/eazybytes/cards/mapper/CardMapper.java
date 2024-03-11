package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardDTO;
import com.eazybytes.cards.entity.Card;

public class CardMapper {

  private CardMapper() {
  }

  public static CardDTO mapToDTO(Card card, CardDTO cardDTO) {
    cardDTO.setCardNumber(card.getCardNumber());
    cardDTO.setCardType(card.getCardType());
    cardDTO.setMobileNumber(card.getMobileNumber());
    cardDTO.setTotalLimit(card.getTotalLimit());
    cardDTO.setAvailableAmount(card.getAvailableAmount());
    cardDTO.setAmountUsed(card.getAmountUsed());
    return cardDTO;
  }

  public static Card mapToEntity(CardDTO cardDTO, Card card) {
    card.setCardNumber(cardDTO.getCardNumber());
    card.setCardType(cardDTO.getCardType());
    card.setMobileNumber(cardDTO.getMobileNumber());
    card.setTotalLimit(cardDTO.getTotalLimit());
    card.setAvailableAmount(cardDTO.getAvailableAmount());
    card.setAmountUsed(cardDTO.getAmountUsed());
    return card;
  }
}

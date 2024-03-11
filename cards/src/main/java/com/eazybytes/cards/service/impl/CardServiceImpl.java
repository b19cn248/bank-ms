package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardDTO;
import com.eazybytes.cards.entity.Card;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardMapper;
import com.eazybytes.cards.repository.CardRepository;
import com.eazybytes.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.eazybytes.cards.constants.CardsConstants.CARD;
import static com.eazybytes.cards.constants.CardsConstants.MOBILE_NUMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

  private final CardRepository repository;

  private final Random random = new Random();

  @Override
  public void createCard(String mobileNumber) {
    log.info("Creating card for mobile number: {}", mobileNumber);

    Optional<Card> card = repository.findByMobileNumber(mobileNumber);

    if (card.isPresent()) {
      log.error("Card already exists for mobile number: {}", mobileNumber);
      throw new CardAlreadyExistsException("Card already exists for mobile number: " + mobileNumber);
    }

    repository.save(createNewCard(mobileNumber));
  }

  @Override
  public CardDTO fetchCard(String mobileNumber) {
    log.info("Fetching card for mobile number: {}", mobileNumber);

    Card card = repository.findByMobileNumber(mobileNumber).orElseThrow(
          () -> new ResourceNotFoundException(CARD, MOBILE_NUMBER, mobileNumber)
    );

    return CardMapper.mapToDTO(card, new CardDTO());
  }

  @Override
  public boolean updateCard(CardDTO cardDTO) {
    log.info("Updating card for mobile number: {}", cardDTO.getMobileNumber());

    Card card = repository.findByMobileNumber(cardDTO.getMobileNumber()).orElseThrow(
          () -> new ResourceNotFoundException(CARD, MOBILE_NUMBER, cardDTO.getMobileNumber())
    );

    CardMapper.mapToEntity(cardDTO, card);

    repository.save(card);

    return true;
  }

  @Override
  public boolean deleteCard(String mobileNumber) {
    log.info("Deleting card for mobile number: {}", mobileNumber);

    Card card = repository.findByMobileNumber(mobileNumber).orElseThrow(
          () -> new ResourceNotFoundException(CARD, MOBILE_NUMBER, mobileNumber)
    );

    repository.delete(card);

    return true;
  }

  private Card createNewCard(String mobileNumber) {

    log.debug("Creating new card for mobile number: {}", mobileNumber);

    Card newCard = new Card();
    long randomCardNumber = 100000000000L + random.nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType(CardsConstants.CREDIT_CARD);
    newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
    return newCard;
  }
}

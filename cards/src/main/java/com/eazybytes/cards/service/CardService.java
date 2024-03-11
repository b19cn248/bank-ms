package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDTO;

public interface CardService {

  /**
   * @param mobileNumber - Mobile Number of the Customer
   */
  void createCard(String mobileNumber);


  /**
   * @param mobileNumber - Input mobile Number
   * @return Card Details based on a given mobileNumber
   */
  CardDTO fetchCard(String mobileNumber);

  /**
   * @param cardDTO - CardDTO Object
   * @return boolean indicating if the update of card details is successful or not
   */

  boolean updateCard(CardDTO cardDTO);


  /**
   * @param mobileNumber - Input Mobile Number
   * @return boolean indicating if the delete of card details is successful or not
   */

  boolean deleteCard(String mobileNumber);
}

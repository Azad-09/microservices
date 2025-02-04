package com.eazycodes.cards.service;

import com.eazycodes.cards.dto.CardsDto;

public interface ICardService {

    /**
     * Creates a new card for a customer
     * @param mobileNumber
     */
    void createCard(String mobileNumber);


    /**
     * Fetch details of the card using mobile number
     * @param mobileNumber
     * @return
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * Update details of the card
     * @param cardsDto
     * @return
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     * Delete details of the card using mobile number
     * @param mobileNumber
     * @return
     */
    boolean deleteCard(String mobileNumber);
}

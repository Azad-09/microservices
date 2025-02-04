package com.eazycodes.cards.service.impl;

import com.eazycodes.cards.constants.CardConstants;
import com.eazycodes.cards.dto.CardsDto;
import com.eazycodes.cards.entity.Cards;
import com.eazycodes.cards.exception.CardAlreadyExistsException;
import com.eazycodes.cards.exception.ResourceNotFoundException;
import com.eazycodes.cards.mapper.CardsMapper;
import com.eazycodes.cards.repository.CardsRepository;
import com.eazycodes.cards.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber - Mobile number of the customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber: " + mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     * Fetches details of the card using mobile number
     * @param mobileNumber
     * @return
     */
    public CardsDto fetchCard(String mobileNumber){
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () ->  new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());

    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber())
        );

        CardsMapper.mapToCards(cards, cardsDto);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "MobileNumber", mobileNumber)
        );

        cardsRepository.deleteById(cards.getCardId());
        return true;
    }


}

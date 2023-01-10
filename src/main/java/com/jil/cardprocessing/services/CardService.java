package com.jil.cardprocessing.services;

import com.jil.cardprocessing.web.model.CardDto;
import com.jil.cardprocessing.web.model.CardPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;


public interface CardService {
    CardPagedList listCards(String cardHolderName, String cardNumber, PageRequest pageRequest);

    CardDto getById(UUID CardId);

    CardDto saveNewCard(CardDto cardDto);

    CardDto updateCard(UUID cardId, CardDto cardDto);

    void deleteCardById(UUID cardId);
}

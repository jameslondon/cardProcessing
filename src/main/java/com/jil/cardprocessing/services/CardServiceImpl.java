package com.jil.cardprocessing.services;


import com.jil.cardprocessing.domain.Card;
import com.jil.cardprocessing.repositories.CardRepository;
import com.jil.cardprocessing.web.controller.NotFoundException;
import com.jil.cardprocessing.web.mappers.CardMapper;
import com.jil.cardprocessing.web.model.CardDto;
import com.jil.cardprocessing.web.model.CardPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Cacheable(cacheNames = "cardListCache")
    @Override
    public CardPagedList listCards(String cardHolderName, String cardNumber, PageRequest pageRequest) {

        CardPagedList cardPagedList;
        Page<Card> cardPage;

        if (!StringUtils.isEmpty(cardHolderName)) {
            cardPage = cardRepository.findAllByCardHolderName(cardHolderName, pageRequest);
        } else if (!StringUtils.isEmpty(cardNumber)) {
            cardPage = cardRepository.findAllByCardNumber(cardNumber, pageRequest);
        } else {
            cardPage = cardRepository.findAll(pageRequest);
        }

            cardPagedList = new CardPagedList(cardPage
                    .getContent()
                    .stream()
                    .map(cardMapper::cardToCardDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(cardPage.getPageable().getPageNumber(),
                                    cardPage.getPageable().getPageSize()),
                    cardPage.getTotalElements());


        return cardPagedList;
    }

    @Override
    public CardDto getById(UUID cardId) {
        return cardMapper.cardToCardDto(cardRepository.findById(cardId).orElseThrow(NotFoundException::new));
    }

    @Override
    public CardDto saveNewCard(CardDto cardDto) {
        return cardMapper.cardToCardDto(cardRepository.save(cardMapper.cardDtoToCard(cardDto)));
    }

    @Override
    public CardDto updateCard(UUID cardId, CardDto cardDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(NotFoundException::new);

        card.setCardHolderName(cardDto.getCardHolderName());
        card.setCardNumber(cardDto.getCardNumber());
        card.setBalance(cardDto.getBalance());
        card.setSpendLimit(cardDto.getSpendLimit());

        return cardMapper.cardToCardDto(cardRepository.save(card));
    }

    @Override
    public void deleteCardById(UUID cardId) {

    }
}

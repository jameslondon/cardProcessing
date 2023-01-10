package com.jil.cardprocessing.web.mappers;

import com.jil.cardprocessing.domain.Card;
import com.jil.cardprocessing.web.model.CardDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CardMapper {

    CardDto cardToCardDto(Card card);
    Card cardDtoToCard(CardDto dto);
}

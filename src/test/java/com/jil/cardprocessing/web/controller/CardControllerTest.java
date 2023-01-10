package com.jil.cardprocessing.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jil.cardprocessing.services.CardService;
import com.jil.cardprocessing.web.model.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CardController.class)
public class CardControllerTest {

    @MockBean
    CardService cardService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CardDto validCard;

    @BeforeEach
    public void setUp() {
        validCard = CardDto.builder().id(UUID.randomUUID())
                .cardHolderName("John Smith")
                .cardNumber("1234 5678 9012 3456")
                .balance(BigDecimal.valueOf(14000.00))
                .spendLimit(BigDecimal.valueOf(2000.00))
                .build();
    }

    @Test
    public void getCard() throws Exception {
        given(cardService.getById(any(UUID.class))).willReturn(validCard);

        mockMvc.perform(get("/api/v1/card/" + validCard.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validCard.getId().toString())))
                .andExpect(jsonPath("$.cardHolderName", is("John Smith")));
    }

    @Test
    public void handlePost() throws Exception {
        //given
        CardDto cardDto = validCard;
        cardDto.setId(null);

        CardDto savedDto = CardDto.builder().id(UUID.randomUUID()).cardHolderName("Jian Liu").cardNumber("8888 5678 9012 3456").build();
        String cardDtoJson = objectMapper.writeValueAsString(cardDto);

        given(cardService.saveNewCard(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void handleUpdate() throws Exception {
        //given
        CardDto cardDto = validCard;
        cardDto.setId(null);
        String cardDtoJson = objectMapper.writeValueAsString(cardDto);

        //when
        mockMvc.perform(put("/api/v1/card/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardDtoJson))
                .andExpect(status().isNoContent());

        then(cardService).should().updateCard(any(), any());

    }
}
package com.jil.cardprocessing.web.controller;

import com.jil.cardprocessing.services.CardService;
import com.jil.cardprocessing.web.model.CardDto;
import com.jil.cardprocessing.web.model.CardPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class CardController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final CardService cardService;

    @GetMapping(produces = { "application/json" }, path = "card")
    public ResponseEntity<CardPagedList> listCards(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "cardHolderName", required = false) String cardHolderName,
                                                   @RequestParam(value = "cardNumber", required = false) String cardNumber) {

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        CardPagedList cardList = cardService.listCards(cardHolderName, cardNumber, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(cardList, HttpStatus.OK);
    }

    @GetMapping("card/{cardId}")
    public ResponseEntity<CardDto> getCardById(@PathVariable("cardId") java.util.UUID cardId) {
        return new ResponseEntity<>(cardService.getById(cardId), HttpStatus.OK);
    }

    @PostMapping(path = "card")
    public ResponseEntity saveNewCard(@RequestBody @Validated CardDto cardDto){

        CardDto savedCard = cardService.saveNewCard(cardDto);

        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/api/v1/card/" + savedCard.getId().toString())
                        .build().toUri())
                .build();
    }

    @PutMapping("card/{cardId}")
    public ResponseEntity updateCardById(@PathVariable("cardId") java.util.UUID cardId, @RequestBody @Validated CardDto cardDto){
        return new ResponseEntity<>(cardService.updateCard(cardId, cardDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("card/{cardId}")
    public ResponseEntity<Void> deleteCardById(@PathVariable("cardId") UUID cardId){

        cardService.deleteCardById(cardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

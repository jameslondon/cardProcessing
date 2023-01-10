package com.jil.cardprocessing.bootstrap;

import com.jil.cardprocessing.domain.Card;
import com.jil.cardprocessing.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class CardLoader implements CommandLineRunner {

    private final CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private synchronized void loadBeerObjects() {
        log.debug("Loading initial card data. Count is: {}", cardRepository.count());

        if (cardRepository.count() == 0) {

            Random random = new Random();

            cardRepository.save(Card.builder()
                    .cardHolderName("Mango Bobs")
                    .cardNumber("1111 0000 0000 0000")
                    .balance(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .spendLimit(BigDecimal.valueOf(5000.00))
                    .build());

            cardRepository.save(Card.builder()
                    .cardHolderName("Jian Liu")
                    .cardNumber("2222 0000 0000 0000")
                    .balance(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .spendLimit(BigDecimal.valueOf(3000.00))
                    .build());

            cardRepository.save(Card.builder()
                    .cardHolderName("John Smith")
                    .cardNumber("3333 0000 0000 0000")
                    .balance(new BigDecimal(BigInteger.valueOf(random.nextInt(10000)), 2))
                    .spendLimit(BigDecimal.valueOf(2000.00))
                    .build());
            log.debug("Card Records loaded: {}", cardRepository.count());
        }
    }

}
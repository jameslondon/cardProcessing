package com.jil.cardprocessing.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jil.cardprocessing.domain.Card;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Page<Card> findAllByCardNumber(String cardNumber, Pageable pageable);

    Page<Card> findAllByCardHolderName(String cardHolderName, Pageable pageable);

}
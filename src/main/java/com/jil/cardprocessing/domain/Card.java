package com.jil.cardprocessing.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.BasicType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Card {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @jakarta.persistence.Column(name = "cardHolderName")
    private String cardHolderName;
    @jakarta.persistence.Column(name = "cardNumber")
    private String cardNumber;

    @jakarta.persistence.Column(name = "balance")
    private BigDecimal balance;
    @jakarta.persistence.Column(name = "spendLimit")
    private BigDecimal spendLimit;
    @CreationTimestamp
    @jakarta.persistence.Column(name = "createdDate", updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @jakarta.persistence.Column(name = "lastModifiedDate")
    private Timestamp lastModifiedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}

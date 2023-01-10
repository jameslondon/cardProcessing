package com.jil.cardprocessing.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    @Null
    private UUID id;

    @NotBlank
    private String cardHolderName;
    @NotBlank
    private String cardNumber;

    private BigDecimal balance;
    private BigDecimal spendLimit;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastUpdatedDate;
}

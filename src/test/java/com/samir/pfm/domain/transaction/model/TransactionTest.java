package com.samir.pfm.domain.transaction.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Transaction Domain Model Tests")
class TransactionTest {

    @Test
    @DisplayName("should create transaction when all valid data is provided")
    void should_create_transaction_when_all_valid_data_is_provided() {
        // Given
        var amount = new BigDecimal("150.00");
        var occurredAt = LocalDateTime.now();
        var description = "Lunch at restaurant";

        // When
        var transaction = new Transaction(
            null, // TransactionId will be null for new aggregate
            amount,
            TransactionType.EXPENSE,
            Category.FOOD,
            occurredAt,
            description
        );

        // Then
        assertAll(
            () -> assertEquals(amount, transaction.getAmount()),
            () -> assertEquals(TransactionType.EXPENSE, transaction.getType()),
            () -> assertEquals(Category.FOOD, transaction.getCategory()),
            () -> assertEquals(occurredAt, transaction.getOccurredAt()),
            () -> assertEquals(description, transaction.getDescription())
        );
    }
}

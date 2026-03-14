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
        var money = Money.dzd(new BigDecimal("150.00"));
        var occurredAt = LocalDateTime.now();
        var description = "Lunch at restaurant";
        var sourceWalletId = WalletId.generate();

        // When
        var transaction = new Transaction(
            null, // TransactionId will be null for new aggregate
            money,
            TransactionType.EXPENSE,
            Category.FOOD,
            sourceWalletId,
            null, // No target wallet for expense
            description,
            occurredAt
        );

        // Then
        assertAll(
            () -> assertEquals(money, transaction.getMoney()),
            () -> assertEquals(TransactionType.EXPENSE, transaction.getType()),
            () -> assertEquals(Category.FOOD, transaction.getCategory()),
            () -> assertEquals(sourceWalletId, transaction.getSourceWalletId()),
            () -> assertNull(transaction.getTargetWalletId()),
            () -> assertEquals(occurredAt, transaction.getOccurredAt()),
            () -> assertEquals(description, transaction.getDescription())
        );
    }

    @Test
    @DisplayName("should throw exception when transfer is created without target wallet")
    void should_throw_exception_when_transfer_is_created_without_target_wallet() {
        // Given
        var money = Money.dzd(new BigDecimal("100.00"));
        var sourceWalletId = WalletId.generate();

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> 
            new Transaction(null, money, TransactionType.TRANSFER, Category.OTHER, sourceWalletId, null, "Transfer", LocalDateTime.now())
        );
    }

    @Test
    @DisplayName("should throw exception when transaction amount is not positive")
    void should_throw_exception_when_transaction_amount_is_not_positive() {
        // Given
        var money = Money.dzd(new BigDecimal("0.00"));
        var sourceWalletId = WalletId.generate();

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> 
            new Transaction(null, money, TransactionType.EXPENSE, Category.FOOD, sourceWalletId, null, "Free lunch?", LocalDateTime.now())
        );
    }
}

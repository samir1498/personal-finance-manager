package com.samir.pfm.application.transaction;

import com.samir.pfm.domain.transaction.model.Category;
import com.samir.pfm.domain.transaction.model.Transaction;
import com.samir.pfm.domain.transaction.model.TransactionType;
import com.samir.pfm.domain.transaction.port.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecordTransaction Service Tests")
class RecordTransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private RecordTransactionService service;

    @Test
    @DisplayName("should save transaction when valid command is executed")
    void should_save_transaction_when_valid_command_is_executed() {
        // Given
        var command = new RecordTransaction.Command(
            new BigDecimal("50.00"),
            TransactionType.EXPENSE,
            Category.TRANSPORT,
            UUID.randomUUID(),
            null,
            "Bus ticket",
            LocalDateTime.now()
        );

        when(repository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        var result = service.execute(command);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(repository, times(1)).save(any(Transaction.class));
    }
}

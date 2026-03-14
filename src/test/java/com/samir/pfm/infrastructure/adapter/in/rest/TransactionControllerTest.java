package com.samir.pfm.infrastructure.adapter.in.rest;

import com.samir.pfm.application.transaction.RecordTransaction;
import com.samir.pfm.domain.transaction.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@DisplayName("Transaction REST Controller Tests")
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecordTransaction recordTransaction;

    @Test
    @DisplayName("should return 201 when transaction is recorded successfully")
    void should_return_201_when_transaction_is_recorded_successfully() throws Exception {
        // Given
        var sourceWalletId = UUID.randomUUID();
        var json = """
            {
                "amount": 150.00,
                "type": "EXPENSE",
                "category": "FOOD",
                "sourceWalletId": "%s",
                "description": "Lunch",
                "occurredAt": "2026-03-14T12:00:00"
            }
            """.formatted(sourceWalletId);

        var transaction = new Transaction(
            TransactionId.generate(),
            Money.dzd(new BigDecimal("150.00")),
            TransactionType.EXPENSE,
            Category.FOOD,
            new WalletId(sourceWalletId),
            null,
            "Lunch",
            LocalDateTime.now()
        );

        when(recordTransaction.execute(any(RecordTransaction.Command.class))).thenReturn(transaction);

        // When / Then
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.money.amount").value(150.00))
                .andExpect(jsonPath("$.type").value("EXPENSE"));
    }
}

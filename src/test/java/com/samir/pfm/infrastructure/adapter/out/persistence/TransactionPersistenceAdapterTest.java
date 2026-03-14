package com.samir.pfm.infrastructure.adapter.out.persistence;

import com.samir.pfm.domain.transaction.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
@DisplayName("Transaction Persistence Adapter Tests (Testcontainers)")
class TransactionPersistenceAdapterTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("pfm_db")
            .withUsername("sa")
            .withPassword("password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Autowired
    private TransactionPersistenceAdapter adapter;

    @Autowired
    private JpaTransactionRepository jpaRepository;

    @Test
    @DisplayName("should save transaction and retrieve it from database")
    void should_save_transaction_and_retrieve_it_from_database() {
        // Given
        var transaction = new Transaction(
            TransactionId.generate(),
            Money.dzd(new BigDecimal("2500.00")),
            TransactionType.INCOME,
            Category.SAVINGS,
            WalletId.generate(),
            null,
            "Salary deposit",
            LocalDateTime.now()
        );

        // When
        var savedTransaction = adapter.save(transaction);

        // Then
        assertNotNull(savedTransaction);
        var entity = jpaRepository.findById(savedTransaction.getId().value()).orElseThrow();
        assertEquals(transaction.getMoney().amount().stripTrailingZeros(), entity.getAmount().stripTrailingZeros());
        assertEquals(transaction.getDescription(), entity.getDescription());
    }
}

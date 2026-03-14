package com.samir.pfm.infrastructure.adapter.out.persistence;

import com.samir.pfm.domain.transaction.model.Category;
import com.samir.pfm.domain.transaction.model.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private UUID sourceWalletId;

    private UUID targetWalletId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    // Hibernate requirement: explicitly providing a no-args constructor
    // because Lombok's @NoArgsConstructor is sometimes not picked up correctly 
    // in complex build environments without explicit annotation processing.
    protected TransactionEntity() {}

    // Manual all-args constructor
    public TransactionEntity(UUID id, BigDecimal amount, String currency, TransactionType type,
                             Category category, UUID sourceWalletId, UUID targetWalletId,
                             String description, LocalDateTime occurredAt) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.category = category;
        this.sourceWalletId = sourceWalletId;
        this.targetWalletId = targetWalletId;
        this.description = description;
        this.occurredAt = occurredAt;
    }

    // Manual getters
    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public TransactionType getType() { return type; }
    public Category getCategory() { return category; }
    public UUID getSourceWalletId() { return sourceWalletId; }
    public UUID getTargetWalletId() { return targetWalletId; }
    public String getDescription() { return description; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}

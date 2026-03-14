package com.samir.pfm.domain.transaction.model;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Transaction {
    private final TransactionId id;
    private final Money money;
    private final TransactionType type;
    private final Category category;
    private final WalletId sourceWalletId;
    private final WalletId targetWalletId; // Optional, for transfers
    private final String description;
    private final LocalDateTime occurredAt;

    public Transaction(TransactionId id, Money money, TransactionType type, Category category,
                       WalletId sourceWalletId, WalletId targetWalletId, String description, LocalDateTime occurredAt) {
        this.id = id;
        this.money = Objects.requireNonNull(money);
        this.type = Objects.requireNonNull(type);
        this.category = Objects.requireNonNull(category);
        this.sourceWalletId = Objects.requireNonNull(sourceWalletId);
        this.targetWalletId = targetWalletId;
        this.description = description;
        this.occurredAt = Objects.requireNonNull(occurredAt);

        validate();
    }

    private void validate() {
        if (type == TransactionType.TRANSFER && targetWalletId == null) {
            throw new IllegalArgumentException("Target wallet is required for transfers");
        }
        if (money.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
    }

    // Manual getters to ensure compilation without Lombok plugin issues in the agent context
    public TransactionId getId() { return id; }
    public Money getMoney() { return money; }
    public TransactionType getType() { return type; }
    public Category getCategory() { return category; }
    public WalletId getSourceWalletId() { return sourceWalletId; }
    public WalletId getTargetWalletId() { return targetWalletId; }
    public String getDescription() { return description; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}

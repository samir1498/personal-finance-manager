package com.samir.pfm.application.transaction;

import com.samir.pfm.domain.transaction.model.Category;
import com.samir.pfm.domain.transaction.model.Transaction;
import com.samir.pfm.domain.transaction.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordTransaction {
    Transaction execute(Command command);

    record Command(
        BigDecimal amount,
        TransactionType type,
        Category category,
        UUID sourceWalletId,
        UUID targetWalletId,
        String description,
        LocalDateTime occurredAt
    ) {}
}

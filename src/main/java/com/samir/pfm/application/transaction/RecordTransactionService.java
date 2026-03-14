package com.samir.pfm.application.transaction;

import com.samir.pfm.domain.transaction.model.*;
import com.samir.pfm.domain.transaction.port.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordTransactionService implements RecordTransaction {

    private final TransactionRepository repository;

    public RecordTransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Transaction execute(Command command) {
        var transaction = new Transaction(
            TransactionId.generate(),
            Money.dzd(command.amount()),
            command.type(),
            command.category(),
            new WalletId(command.sourceWalletId()),
            command.targetWalletId() != null ? new WalletId(command.targetWalletId()) : null,
            command.description(),
            command.occurredAt()
        );

        return repository.save(transaction);
    }
}

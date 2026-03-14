package com.samir.pfm.infrastructure.adapter.out.persistence;

import com.samir.pfm.domain.transaction.model.*;
import com.samir.pfm.domain.transaction.port.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionPersistenceAdapter implements TransactionRepository {

    private final JpaTransactionRepository repository;

    public TransactionPersistenceAdapter(JpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = toEntity(transaction);
        var savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public List<Transaction> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private TransactionEntity toEntity(Transaction domain) {
        return new TransactionEntity(
            domain.getId().value(),
            domain.getMoney().amount(),
            domain.getMoney().currency(),
            domain.getType(),
            domain.getCategory(),
            domain.getSourceWalletId().value(),
            domain.getTargetWalletId() != null ? domain.getTargetWalletId().value() : null,
            domain.getDescription(),
            domain.getOccurredAt()
        );
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
            new TransactionId(entity.getId()),
            new Money(entity.getAmount(), entity.getCurrency()),
            entity.getType(),
            entity.getCategory(),
            new WalletId(entity.getSourceWalletId()),
            entity.getTargetWalletId() != null ? new WalletId(entity.getTargetWalletId()) : null,
            entity.getDescription(),
            entity.getOccurredAt()
        );
    }
}

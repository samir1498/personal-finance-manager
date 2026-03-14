package com.samir.pfm.domain.transaction.port;

import com.samir.pfm.domain.transaction.model.Transaction;
import java.util.Optional;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(String id);
    List<Transaction> findAll();
}

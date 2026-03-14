package com.samir.pfm.infrastructure.adapter.in.rest;

import com.samir.pfm.application.transaction.RecordTransaction;
import com.samir.pfm.domain.transaction.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final RecordTransaction recordTransaction;

    public TransactionController(RecordTransaction recordTransaction) {
        this.recordTransaction = recordTransaction;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction recordTransaction(@Valid @RequestBody RecordTransaction.Command command) {
        return recordTransaction.execute(command);
    }
}

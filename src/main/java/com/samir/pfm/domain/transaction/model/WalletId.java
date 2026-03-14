package com.samir.pfm.domain.transaction.model;

import java.util.UUID;

public record WalletId(UUID value) {
    public static WalletId generate() {
        return new WalletId(UUID.randomUUID());
    }
}

CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    amount NUMERIC(38, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    type VARCHAR(20) NOT NULL,
    category VARCHAR(50) NOT NULL,
    source_wallet_id UUID NOT NULL,
    target_wallet_id UUID,
    description VARCHAR(255) NOT NULL,
    occurred_at TIMESTAMP NOT NULL
);

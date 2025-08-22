package org.mspadaru.banking.account.service.model;

import java.time.LocalDateTime;

public record Transaction(LocalDateTime date, int Amount, long Balance, TransactionType type) {
}

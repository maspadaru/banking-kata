package org.mspadaru.banking.account.formatter;

import org.mspadaru.banking.account.service.model.Transaction;

import java.util.List;

public interface StatementFormatter {
    String format(List<Transaction> transactions);
}

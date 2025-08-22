package org.mspadaru.banking.account.output;

import org.mspadaru.banking.account.service.model.Transaction;

import java.util.List;

public interface StatementPrinter {
    void printStatement(List<Transaction> transactions);
}

package org.mspadaru.banking.account.service;

import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.output.StatementPrinter;
import org.mspadaru.banking.account.service.model.Transaction;
import org.mspadaru.banking.account.service.model.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultAccount implements Account {

    private final StatementPrinter statementPrinter;
    private final List<Transaction> transactions;
    private long balance;

    public DefaultAccount(StatementPrinter statementPrinter) {
        this.statementPrinter = statementPrinter;
        this.transactions = new ArrayList<>();
    }

    @Override
    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            Transaction transaction = new Transaction(LocalDateTime.now(), amount, balance, TransactionType.DEPOSIT);
            transactions.add(transaction);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void withdraw(int amount) {
        balance -= amount;
        Transaction transaction = new Transaction(LocalDateTime.now(), amount, balance, TransactionType.WITHDRAW);
        transactions.add(transaction);
    }

    @Override
    public void printStatement() {
        statementPrinter.printStatement(transactions);
    }
}

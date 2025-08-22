package org.mspadaru.banking.account.service;

import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.output.StatementPrinter;
import org.mspadaru.banking.account.service.model.Transaction;

import java.util.List;

public class DefaultAccount implements Account {

    private StatementPrinter statementPrinter;
    private List<Transaction> transactions;

    public DefaultAccount(StatementPrinter statementPrinter) {
        this.statementPrinter = statementPrinter;
    }

    @Override
    public void deposit(int amount) {

    }

    @Override
    public void withdraw(int amount) {

    }

    @Override
    public void printStatement() {
        statementPrinter.printStatement(transactions);
    }
}

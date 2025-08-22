package org.mspadaru.banking.account.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.output.StatementPrinter;
import org.mspadaru.banking.account.service.model.Transaction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultAccountTest {

    @Test
    void deposit_positiveAmount_printStatementPrintsCorrectTransaction() {
        StatementPrinter mockPrinter = mock(StatementPrinter.class);
        Account account = new DefaultAccount(mockPrinter);

        account.deposit(100);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockPrinter).printStatement(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.getFirst();
        assertEquals(100, transaction.Amount());
        assertEquals(100, transaction.Balance());
        assertNotNull(transaction.date());
    }

}
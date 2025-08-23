package org.mspadaru.banking.account.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.formatter.StatementFormatter;
import org.mspadaru.banking.account.service.model.Transaction;
import org.mspadaru.banking.account.service.model.TransactionType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultAccountTest {

    @Test
    void printStatement_withTransactions_callsFormatter() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        account.deposit(1000);

        account.printStatement();

        verify(mockFormatter).format(anyList());
    }

    @Test
    void withdraw_positiveBalance_printStatementPrintsCorrectTransaction() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);

        account.deposit(1000);
        account.withdraw(100);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFormatter).format(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(2, transactions.size());
        Transaction transaction = transactions.get(1);
        assertTransaction(transaction, TransactionType.WITHDRAW, 100, 900);
        assertNotNull(transaction.date());
    }

    @Test
    void withdraw_amountMoreThanBalance_throwsException() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        account.deposit(100);
        assertThrows(IllegalStateException.class, () -> account.withdraw(200));
    }

    @Test
    void withdraw_zeroAmount_throwsException() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
    }

    @Test
    void withdraw_negativeAmount_throwsException() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100));
    }

    @Test
    void withdraw_multipleTransactions_reflectedInStatement() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);

        account.deposit(1000);
        account.withdraw(100);
        account.withdraw(200);
        account.withdraw(300);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFormatter).format(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(4, transactions.size());

        Transaction second = transactions.get(1);
        Transaction third = transactions.get(2);
        Transaction fourth = transactions.get(3);

        assertTransaction(second, TransactionType.WITHDRAW, 100, 900);
        assertTransaction(third, TransactionType.WITHDRAW, 200, 700);
        assertTransaction(fourth, TransactionType.WITHDRAW, 300, 400);
    }

    @Test
    void deposit_positiveAmount_printStatementPrintsCorrectTransaction() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);

        account.deposit(100);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFormatter).format(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.getFirst();
        assertTransaction(transaction, TransactionType.DEPOSIT, 100, 100);
        assertNotNull(transaction.date());
    }


    @Test
    void deposit_negativeAmount_throwsException() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
    }

    @Test
    void deposit_zeroAmount_throwsException() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
    }

    @Test
    void deposit_multipleSmallDeposits_accumulateBalanceAndPrintAllTransactions() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);

        account.deposit(100);
        account.deposit(200);
        account.deposit(300);
        account.deposit(400);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFormatter).format(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(4, transactions.size());

        Transaction first = transactions.get(0);
        Transaction second = transactions.get(1);
        Transaction third = transactions.get(2);
        Transaction fourth = transactions.get(3);

        assertTransaction(first, TransactionType.DEPOSIT, 100, 100);
        assertTransaction(second, TransactionType.DEPOSIT, 200, 300);
        assertTransaction(third, TransactionType.DEPOSIT, 300, 600);
        assertTransaction(fourth, TransactionType.DEPOSIT, 400, 1000);
    }


    @Test
    void deposit_largeDeposit_registersAndDoesNotOverflowBalance() {
        StatementFormatter mockFormatter = mock(StatementFormatter.class);
        Account account = new DefaultAccount(mockFormatter);

        account.deposit(100);
        account.deposit(Integer.MAX_VALUE);
        account.printStatement();

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFormatter).format(captor.capture());
        List<Transaction> transactions = captor.getValue();
        assertEquals(2, transactions.size());
        Transaction second = transactions.get(1);
        long expectedBalance = 100L + Integer.MAX_VALUE;
        assertTransaction(second, TransactionType.DEPOSIT, Integer.MAX_VALUE, expectedBalance);
    }

    private void assertTransaction(Transaction t, TransactionType expectedType, int expectedAmount,
                                   long expectedBalance) {
        assertEquals(expectedType, t.type());
        assertEquals(expectedAmount, t.amount());
        assertEquals(expectedBalance, t.balance());
    }

}
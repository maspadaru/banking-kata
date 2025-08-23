package org.mspadaru.banking.account.formatter;

import org.junit.jupiter.api.Test;
import org.mspadaru.banking.account.service.model.Transaction;
import org.mspadaru.banking.account.service.model.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultStatementFormatterTest {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void format_noTransactions_returnsOnlyTableHead() {
        StatementFormatter sf = new DefaultStatementFormatter();
        String result = sf.format(List.of());
        String expected = getTableHead();
        assertEquals(expected.trim(), result.trim());
    }

    @Test
    void format_oneTransaction_returnsCorrectlyFormatedString() {
        StatementFormatter sf = new DefaultStatementFormatter();
        LocalDateTime date = LocalDateTime.of(2025, 8, 21, 0, 0);
        Transaction t = new Transaction(date, 100, 100, TransactionType.DEPOSIT);
        String result = sf.format(List.of(t));
        String expected = getTableHead();
        expected += getTransactionLine(t);
        assertEquals(expected.trim(), result.trim());
    }

    @Test
    void format_multipleTransactions_returnsCorrectlyFormatedString() {
        StatementFormatter sf = new DefaultStatementFormatter();
        LocalDateTime date = LocalDateTime.of(2025, 8, 21, 0, 0);
        Transaction t1 = new Transaction(date, 1000, 1000, TransactionType.DEPOSIT);
        Transaction t2 = new Transaction(date, 200, 800, TransactionType.WITHDRAW);
        Transaction t3 = new Transaction(date, 300, 500, TransactionType.WITHDRAW);
        Transaction t4 = new Transaction(date, 400, 900, TransactionType.DEPOSIT);
        String result = sf.format(List.of(t1, t2, t3, t4));
        String expected = getTableHead();
        expected += getTransactionLine(t1);
        expected += getTransactionLine(t2);
        expected += getTransactionLine(t3);
        expected += getTransactionLine(t4);
        assertEquals(expected.trim(), result.trim());
    }

    @Test
    void format_oneTransactionWithLargeAmount_returnsCorrectlyFormatedString() {
        StatementFormatter sf = new DefaultStatementFormatter();
        LocalDateTime date = LocalDateTime.of(2025, 8, 21, 0, 0);
        Transaction t = new Transaction(date, Integer.MAX_VALUE, Long.MAX_VALUE, TransactionType.DEPOSIT);
        String result = sf.format(List.of(t));
        String expected = getTableHead();
        expected += getTransactionLine(t);
        assertEquals(expected.trim(), result.trim());
    }

    private String getTableHead() {
        return String.format("%-12s %7s %8s%n", "Date", "Amount", "Balance");
    }

    private String getTransactionLine(Transaction t) {
        String formattedDate = t.date().format(DATE_FORMATTER);
        int amount = (t.type() == TransactionType.DEPOSIT) ? t.amount() : -t.amount();
        return String.format("%-12s %+7d %8d%n", formattedDate, amount, t.balance());
    }

}
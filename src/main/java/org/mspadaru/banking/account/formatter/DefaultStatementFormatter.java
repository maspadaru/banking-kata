package org.mspadaru.banking.account.formatter;

import org.mspadaru.banking.account.service.model.Transaction;
import org.mspadaru.banking.account.service.model.TransactionType;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DefaultStatementFormatter implements StatementFormatter {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public String format(List<Transaction> transactions) {
        StringBuilder result = new StringBuilder();
        result.append(getTableHead());
        for (Transaction t : transactions) {
            result.append(formatTransaction(t));
        }
        return result.toString();
    }

    private String getTableHead() {
        return String.format("%-12s %7s %8s%n", "Date", "Amount", "Balance");
    }

    private String formatTransaction(Transaction t) {
        String formattedDate = t.date().format(DATE_FORMATTER);
        int amount = (t.type() == TransactionType.DEPOSIT) ? t.amount() : -t.amount();
        return String.format("%-12s %+7d %8d%n", formattedDate, amount, t.balance());
    }

}

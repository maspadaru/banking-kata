package org.mspadaru.banking.account.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mspadaru.banking.account.service.model.Transaction;

import java.util.List;

public class JsonStatementFormatter implements StatementFormatter {
    private final ObjectMapper objectMapper;

    public JsonStatementFormatter() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 👈 Add this line
    }

    @Override
    public String format(List<Transaction> transactions) {
        try {
            return objectMapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert transactions to JSON", e);
        }
    }
}

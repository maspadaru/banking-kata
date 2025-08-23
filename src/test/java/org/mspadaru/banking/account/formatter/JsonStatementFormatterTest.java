package org.mspadaru.banking.account.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mspadaru.banking.account.service.model.Transaction;
import org.mspadaru.banking.account.service.model.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonStatementFormatterTest {

    @Test
    void format_transactions_returnsJsonString() {
        StatementFormatter formatter = new JsonStatementFormatter();
        LocalDateTime date = LocalDateTime.of(2025, 8, 21, 0, 0);
        Transaction original = new Transaction(date, 100, 100, TransactionType.DEPOSIT);
        List<Transaction> originalList = List.of(original);

        String json = formatter.format(originalList);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // 👈 Add this line
            List<Transaction> deserialized = objectMapper.readValue(json, new TypeReference<>() {
            });
            assertEquals(originalList, deserialized);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void format_emptyList_returnsEmptyJsonArray() {
        StatementFormatter formatter = new JsonStatementFormatter();
        String json = formatter.format(List.of());
        assertEquals("[]", json);
    }

}
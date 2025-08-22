package org.mspadaru.banking.account.output;

import org.mspadaru.banking.account.service.model.Transaction;

public interface StatementFormater {

    void formatStatement(Transaction transaction);

}

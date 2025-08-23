package org.mspadaru.banking.account.api;

import org.mspadaru.banking.account.formatter.DefaultStatementFormatter;
import org.mspadaru.banking.account.service.DefaultAccount;

public class DefaultAccountFactory implements AccountFactory {

    @Override
    public Account createAccount() {
        return new DefaultAccount(new DefaultStatementFormatter());
    }

}

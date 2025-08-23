package org.mspadaru.banking.account.factory;

import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.api.AccountFactory;
import org.mspadaru.banking.account.formatter.JsonStatementFormatter;
import org.mspadaru.banking.account.service.DefaultAccount;

public class JsonAccountFactory implements AccountFactory {
    @Override
    public Account createAccount() {
        return new DefaultAccount(new JsonStatementFormatter());
    }
}

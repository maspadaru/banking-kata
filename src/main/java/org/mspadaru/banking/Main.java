package org.mspadaru.banking;

import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.api.AccountFactory;
import org.mspadaru.banking.account.api.DefaultAccountFactory;

public class Main {
    public static void main(String[] args) {
        AccountFactory factory = new DefaultAccountFactory();
        Account account = factory.createAccount();
        account.deposit(500);
        account.withdraw(100);
        System.out.println(account.printStatement());
    }
}
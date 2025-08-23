package org.mspadaru.banking;

import org.mspadaru.banking.account.api.Account;
import org.mspadaru.banking.account.api.AccountFactory;
import org.mspadaru.banking.account.factory.DefaultAccountFactory;
import org.mspadaru.banking.account.factory.JsonAccountFactory;

public class Main {
    public static void main(String[] args) {
        AccountFactory defaultAccountFactory = new DefaultAccountFactory();
        Account defaultAccount = defaultAccountFactory.createAccount();
        defaultAccount.deposit(500);
        defaultAccount.withdraw(100);
        System.out.println(defaultAccount.printStatement());

        System.out.println();

        AccountFactory jsonAccountFactory = new JsonAccountFactory();
        Account jsonAccount = jsonAccountFactory.createAccount();
        jsonAccount.deposit(500);
        jsonAccount.withdraw(100);
        System.out.println(jsonAccount.printStatement());
    }
}
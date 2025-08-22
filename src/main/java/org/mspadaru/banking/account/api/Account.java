package org.mspadaru.banking.account.api;

public interface Account {

    void deposit(int amount);

    void withdraw(int amount);

    String printStatement();

}

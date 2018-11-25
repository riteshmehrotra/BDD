package com.it.exercise.domain;

import com.it.exercise.domain.accounts.Account;
import com.it.exercise.exceptions.InvalidAccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class AccountHolder {
    String accountHolderName;
    List<Account> accounts;

    public AccountHolder(String name) {
        this.accountHolderName = name;
        accounts=new ArrayList<>();
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void allocate(Account account) {
        accounts.add(account);
    }

    public Account getAccount(final String accNumber) throws InvalidAccountException {
        Optional<Account> accountSought= accounts.stream().filter(account->account.getAccountNumber().equals(accNumber)).findFirst();
        if(accountSought.isPresent())
            return accountSought.get();
        throw new InvalidAccountException();
    }

    public Account getAccount(final AccountType accountType) throws InvalidAccountException {
        Optional<Account> accountSought= accounts.stream().filter(account->account.getAccountType().equals(accountType)).findFirst();
        if(accountSought.isPresent())
            return accountSought.get();
        throw new InvalidAccountException();
    }

    public List<Account> getAllAccounts(){
        return accounts;
    }
}

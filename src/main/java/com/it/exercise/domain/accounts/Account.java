package com.it.exercise.domain.accounts;

import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.facilities.ApplicableServices;
import com.it.exercise.domain.rules.AccountRules;
import com.it.exercise.domain.services.Transaction;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.TransferLimitExceeded;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by TechTalks on 1/14/2018.
 */
public abstract class Account implements ApplicableServices {
    String accountNumber;
    AccountType accountType;
    BigDecimal accountBalance = BigDecimal.ZERO;

    List<Currency> currencies=new ArrayList<>();
    List<Transaction> transactions;
    List<AccountRules> rules;

    boolean isActive;

    public Account(String accountNumber, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.isActive = true;
        transactions = new ArrayList<>();
        this.currencies.add(Currency.getInstance("SGD"));
        rules = new ArrayList<>();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return accountBalance;
    }

    public abstract int deposit(Transaction transaction) throws OperationNotSupportedException, DepositFailedException;

    public String getAccountNumber() {
        return accountNumber;
    }

    public abstract int withdraw(Transaction transaction) throws InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded;

    public void deactivateAccount() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}

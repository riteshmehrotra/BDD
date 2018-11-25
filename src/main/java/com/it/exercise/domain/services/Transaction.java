package com.it.exercise.domain.services;

import com.it.exercise.domain.accounts.Account;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.TransferLimitExceeded;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by TechTalks on 1/14/2018.
 */
public abstract class Transaction {
    public Transaction() {
        Random random = new Random();
        this.transactionId =  random.nextInt(100);
        System.out.print(this.transactionId);
    }


    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }


    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    public int getTransactionId() {
        return transactionId;
    }

    Account fromAccount;
    Account toAccount;
    BigDecimal amount;
    String note;
    int transactionId;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    TransactionType transactionType;

    public abstract int submit() throws InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded;

    abstract int rollback(boolean isWithDrawnFromSource) throws DepositFailedException, OperationNotSupportedException;

    public abstract Transaction copy();
}

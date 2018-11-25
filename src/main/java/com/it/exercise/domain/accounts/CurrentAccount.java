package com.it.exercise.domain.accounts;

import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.services.Transaction;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.TransferLimitExceeded;

import javax.naming.OperationNotSupportedException;

/**
 * Created by TechTalks on 11/17/2018.
 */
public class CurrentAccount extends Account {
    public CurrentAccount(String autoGeneratedAccountNumber, AccountType accountType) {
        super(autoGeneratedAccountNumber,accountType);
    }

    @Override
    public int deposit(Transaction transaction) throws OperationNotSupportedException, DepositFailedException {
        return 0;
    }

    @Override
    public int withdraw(Transaction transaction) throws InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded {
        return 0;
    }

    @Override
    public boolean isFundsTransferApplicable() {
        return false;
    }

    @Override
    public boolean isStatementGenerationApplicable() {
        return false;
    }

    @Override
    public boolean isOverdraftApplicable() {
        return false;
    }
}

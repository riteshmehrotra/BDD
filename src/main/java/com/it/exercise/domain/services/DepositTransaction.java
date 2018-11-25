package com.it.exercise.domain.services;

import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.naming.OperationNotSupportedException;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class DepositTransaction extends Transaction {
    public DepositTransaction() {
        super();
    }

    public int submit() throws InsufficientBalanceException, OperationNotSupportedException, DepositFailedException {
        try {
            return toAccount.deposit(this);
        } catch (Exception ex) {
            return -1;
        }
    }

    public int rollback(boolean isWithDrawnFromSource) throws DepositFailedException, OperationNotSupportedException {
        throw new NotImplementedException();
    }

    @Override
    public Transaction copy() {
        Transaction copyOfTransaction = new DepositTransaction();
        return  copyOfTransaction;
    }
}

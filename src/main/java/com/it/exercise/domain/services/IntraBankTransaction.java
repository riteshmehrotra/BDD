package com.it.exercise.domain.services;

import com.it.exercise.domain.accounts.Account;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.TransferLimitExceeded;
import com.sun.media.jfxmedia.logging.Logger;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class IntraBankTransaction extends Transaction {
    public IntraBankTransaction(Account fromAccount, Account toAccount, BigDecimal amount) {
        super();
        this.setFromAccount(fromAccount);
        this.setToAccount(toAccount);
        this.setAmount(amount);

    }

    public int submit() throws InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded {
        boolean isWithdrawnFromSource = false;
        try {
            fromAccount.withdraw(this);
            toAccount.deposit(this);
            return this.getTransactionId();
        } catch (DepositFailedException ex) {
            this.rollback(isWithdrawnFromSource);
            return -1;
        }
    }

    public int rollback(boolean isWithDrawnFromSource) throws OperationNotSupportedException {
        try {
                return fromAccount.deposit(this);
        } catch (DepositFailedException ex) {
            Logger.logMsg(4, ex.getMessage());
        }
        return -1;
    }

    @Override
    public Transaction copy() {
        Transaction copyOfTransaction = new IntraBankTransaction(this.fromAccount, this.toAccount, this.amount);
        return  copyOfTransaction;
    }
}

package com.it.exercise.domain.accounts;

import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.services.Transaction;
import com.it.exercise.domain.services.TransactionType;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.TransferLimitExceeded;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class MultiCurrencyAccount extends Account {
    private BigDecimal minimumBalance = BigDecimal.ZERO;
    private BigDecimal dailyTransactionLimit = new BigDecimal(50);
    public MultiCurrencyAccount(String accountNumber, AccountType accountType) {
        super(accountNumber, accountType);
        addCurrencies();
    }

    private void addCurrencies() {
        this.currencies.add(Currency.getInstance("INR"));
        this.currencies.add(Currency.getInstance("JPY"));
        this.currencies.add(Currency.getInstance("CNY"));

    }

    @Override
    public int deposit(Transaction transaction) throws DepositFailedException {
        if(this.isFundsTransferApplicable() && isActive()) {
            accountBalance = accountBalance.add(transaction.getAmount());
            Transaction copyOfTransaction = transaction.copy();
            copyOfTransaction.setTransactionType(TransactionType.Credit);
            transactions.add(copyOfTransaction);
            return  transaction.getTransactionId();
        }
        else
            throw new DepositFailedException(String.format("Deposit service is not available for your account"));
    }

    @Override
    public int withdraw(Transaction transaction) throws InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded {
        if (this.canWithdraw(transaction.getAmount())) {
            accountBalance = accountBalance.subtract(transaction.getAmount());
            Transaction copyOfTransaction = transaction.copy();
            copyOfTransaction.setTransactionType(TransactionType.Debit);
            transactions.add(copyOfTransaction);
            return  transaction.getTransactionId();
        } else
            throw new OperationNotSupportedException(String.format("Fund transfer is not available for your account"));
    }

    private boolean canWithdraw(BigDecimal amount) throws InsufficientBalanceException, TransferLimitExceeded {
        if (isFundsTransferApplicable() && isActive()) {
            if (sufficientBalanceExists(amount)){
                if(withinDailyTransferLimit(amount))
                    return true;
                else
                    throw new TransferLimitExceeded();
            }
            else
                throw new InsufficientBalanceException("You do not have sufficient balance for this transaction");
        }
        return false;
    }

    private boolean withinDailyTransferLimit(BigDecimal amount) {
        List<Transaction> allDebitTransaction =transactions.stream().filter(transaction -> transaction.getTransactionType().equals(TransactionType.Debit))
                .collect(Collectors.toList());
        BigDecimal sumOfAllDebitTransactions= allDebitTransaction.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        if(sumOfAllDebitTransactions.compareTo(dailyTransactionLimit)>=0)
            return  false;
        return  true;
    }

    private boolean sufficientBalanceExists(BigDecimal amount) {
        return this.getBalance().subtract(this.minimumBalance).compareTo(amount) > 0;
    }

    @Override
    public boolean isFundsTransferApplicable() {
        return true;
    }

    @Override
    public boolean isStatementGenerationApplicable() {
        return true;
    }

    @Override
    public boolean isOverdraftApplicable() {
        return false;
    }
}

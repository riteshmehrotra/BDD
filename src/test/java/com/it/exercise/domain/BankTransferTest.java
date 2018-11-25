package com.it.exercise.domain;

import com.it.exercise.domain.accounts.Account;
import com.it.exercise.domain.services.*;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.InvalidAccountException;
import com.it.exercise.exceptions.TransferLimitExceeded;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class BankTransferTest {
    Account fromAccount;
    Account toAccount;
    AccountNumberGenerator accountNumberGenerator = Mockito.mock(AccountNumberGenerator.class);
    @Before
    public void setup() throws InvalidAccountException, DepositFailedException, OperationNotSupportedException, InsufficientBalanceException, TransferLimitExceeded {
        AccountServices.initialize();
        setupSourceAccount();
        setupDestinationAccount();

    }

    @Test
    public void testTransfer() throws InvalidAccountException, InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        BigDecimal transferAmount = new BigDecimal(50);
        int transactionId = 0;
        transactionId = transferMoney(fromAccount, toAccount, transferAmount);

        assertTrue(transactionId > 0);
        assertThat(fromAccount.getBalance(), is(equalTo(new BigDecimal(450))));
        assertThat(toAccount.getBalance(), is(equalTo(new BigDecimal(250))));
    }


    @Test(expected = InsufficientBalanceException.class)
    public void testTransferFailsForInsufficientBalanceAtSource() throws InvalidAccountException, InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        BigDecimal amount = new BigDecimal(600);
        new IntraBankTransaction(fromAccount, toAccount, amount).submit();
    }

    @Test(expected = TransferLimitExceeded.class)
    public void testTransferFailsIfDailyTransferLimitExceededAtSource() throws InvalidAccountException, InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        BigDecimal transferAmount = new BigDecimal(400);
        transferMoney(fromAccount, toAccount, transferAmount);
        BigDecimal secondTransferAmount = new BigDecimal(110);
        transferMoney(fromAccount, toAccount, secondTransferAmount);
    }

    private int transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) throws InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        Transaction transaction = new IntraBankTransaction(fromAccount, toAccount, amount);
        return transaction.submit();
    }

    private void setupDestinationAccount() throws InvalidAccountException, DepositFailedException, OperationNotSupportedException, InsufficientBalanceException, TransferLimitExceeded {
        toAccount = setAccount(new BigDecimal(200),"103444");
    }

    private void setupSourceAccount() throws InvalidAccountException, DepositFailedException, OperationNotSupportedException, InsufficientBalanceException, TransferLimitExceeded {
        fromAccount = setAccount(new BigDecimal(500),"101110");
    }

    private Account setAccount(BigDecimal deposit, String accountNumber) throws InvalidAccountException, DepositFailedException, OperationNotSupportedException, InsufficientBalanceException, TransferLimitExceeded {
        Mockito.when(accountNumberGenerator.generate()).thenReturn(accountNumber);
        Account account = AccountServices.create(accountNumberGenerator, AccountType.SAVINGS);
        depositMoney(deposit, account);
        return account;
    }

    private void depositMoney(BigDecimal deposit, Account account) throws InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        Transaction transaction = new DepositTransaction();
        transaction.setToAccount(account);
        transaction.setAmount(deposit);
        transaction.submit();
    }
}


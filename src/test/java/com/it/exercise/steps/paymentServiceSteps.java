package com.it.exercise.steps;

import com.it.exercise.domain.AccountHolder;
import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.accounts.Account;
import com.it.exercise.domain.services.*;
import com.it.exercise.exceptions.DepositFailedException;
import com.it.exercise.exceptions.InsufficientBalanceException;
import com.it.exercise.exceptions.InvalidAccountException;
import com.it.exercise.exceptions.TransferLimitExceeded;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.mockito.Mockito;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by TechTalks on 3/18/2018.
 */
public class paymentServiceSteps {
    AccountNumberGenerator accountNumberGenerator = Mockito.mock(AccountNumberGenerator.class);
    List<AccountHolder> accountHolders = new ArrayList<>();
    Exception catchedException;

    @Given("Below customer accounts $list")
    public void givenAccounts(@Named("list") ExamplesTable table) throws InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded, DepositFailedException {
        for (Map<String,String> row: table.getRows()){
            Mockito.when(accountNumberGenerator.generate()).thenReturn(row.get("AccountNumber"));
            AccountHolder accHolder = new AccountHolder(row.get("AccountHolder"));
            Account account = AccountServices.create(accountNumberGenerator, AccountType.valueOf(row.get("AccountType")));
            depositToAccount(account,new BigDecimal(row.get("Balance")));
            accountHolders.add(accHolder);
            accHolder.allocate(account);
        }
    }

    private void depositToAccount(Account account, BigDecimal amount) throws InsufficientBalanceException, OperationNotSupportedException, DepositFailedException, TransferLimitExceeded {
        Transaction transaction=new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setToAccount(account);
        transaction.submit();

    }

    @Given("$name has SGD $amount in account $accNumber")
    public void givenDeposit(@Named("name") String name, @Named("amount")BigDecimal amount, @Named("accNumber") String accNumber) throws InvalidAccountException, InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded, DepositFailedException {
        Optional<AccountHolder> accHolder= accountHolders.stream().filter(accountHolder -> accountHolder.getAccountHolderName().equals(name)).findFirst();
        Transaction transaction=new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setToAccount(accHolder.get().getAccount(accNumber));
        transaction.submit();
    }

    @When("$name initiates a funds transfer of SGD $amount to $payee account $payeeAccount")
    public void whenTransferreed(@Named("name") String name, @Named("amount") BigDecimal amount, @Named("payee") String payeeName, @Named("payeeAccount") String payeeAcc) throws InvalidAccountException, InsufficientBalanceException, OperationNotSupportedException, TransferLimitExceeded, DepositFailedException {
        try {
            Optional<AccountHolder> payer = accountHolders.stream().filter(accountHolder -> accountHolder.getAccountHolderName().equals(name)).findFirst();
            Optional<AccountHolder> payee = accountHolders.stream().filter(accountHolder -> accountHolder.getAccountHolderName().equals(payeeName)).findFirst();
            Account payerAccount = payer.get().getAllAccounts().get(0);
            Transaction transaction = new IntraBankTransaction(payerAccount,
                    payee.get().getAccount(payeeAcc), amount);
            transaction.submit();
        }
        catch (Exception ex){
            catchedException = ex;
        }
    }

    @Then("$name receives error: $message")
    public void thenException(@Named("name") String name, @Named("message") String message){
        assertThat(catchedException.getMessage(),is(equalTo(message)));
    }

    @Then("$name new balance for account $accNumber should be SGD $amount")
    public void thenBalance(@Named("name") String name, @Named("accNumber") String accountNumber, @Named("amount") BigDecimal amount) throws InvalidAccountException {
        Optional<AccountHolder> payer= accountHolders.stream().filter(accountHolder -> accountHolder.getAccountHolderName().equals(name)).findFirst();
        assertThat(payer.get().getAccount(accountNumber).getBalance(), is(equalTo(amount)));
    }
}

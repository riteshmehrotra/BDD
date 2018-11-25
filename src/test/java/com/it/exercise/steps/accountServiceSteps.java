package com.it.exercise.steps;

import com.it.exercise.domain.AccountHolder;
import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.accounts.Account;
import com.it.exercise.domain.services.AccountNumberGenerator;
import com.it.exercise.domain.services.AccountServices;
import com.it.exercise.exceptions.InvalidAccountException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.mockito.Mockito;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by TechTalks on 3/17/2018.
 */
public class accountServiceSteps {
    AccountHolder accountHolder;
    AccountNumberGenerator accountNumberGenerator= mock(AccountNumberGenerator.class);



    @Given("$name is a new customer")
    public void given(@Named("name") String name) {
        accountHolder = new AccountHolder(name);
    }

    @When("he opens a new $type account")
    public void when(@Named("type") String type) {
        Mockito.when(accountNumberGenerator.generate()).thenReturn("10110");
        accountHolder.allocate(AccountServices.create(accountNumberGenerator, AccountType.valueOf(type)));
    }


    @Then("he should get list of accounts $details")
    public void thenGetSummary(@Named("details") ExamplesTable table){
        int counter=0;
        List<Account> allAccounts = accountHolder.getAllAccounts();
        for(Map<String, String> row: table.getRows()){
            assertThat(allAccounts.get(counter).getAccountType().toString(),is(equalTo(row.get("Type"))));
            assertThat(allAccounts.get(counter).getAccountNumber(),is(equalTo(row.get("Number"))));
            counter++;
        }
    }

    @Then("he should receive an account number")
    public void then() {
        Account account = accountHolder.getAllAccounts().get(0);
        assertThat(account.getAccountNumber(), is(notNullValue()));
    }

    @Then("the account type should be $type")
    public void thenTypeOfAccount(@Named("type") String type) throws InvalidAccountException {
        assertThat(accountHolder.getAccount(AccountType.valueOf(type)), is(notNullValue()));
    }

    @Then("total number of accounts should be $count")
    public void thenNumberOfAccounts(@Named("count") int count) {
        assertThat(accountHolder.getAllAccounts().size(), is(equalTo(count)));
    }

    @Then("available currencies should be $list")
    public void thenCurrencies(@Named("list") ExamplesTable table) throws InvalidAccountException {
        List<Currency> currencies = accountHolder.getAccount(AccountType.MULTICURRENCY).getCurrencies();
        int counter = 0;
        System.out.println(table.getRows().size());
        System.out.println("Row size");
        assertThat("The number of currencies should match",currencies.size(),is(equalTo(table.getRowCount())));
        for (Map<String, String> row : table.getRows()) {
            assertThat(currencies.get(counter).getCurrencyCode(), is(equalTo(row.get("Currency"))));
            counter++;
        }
    }
}


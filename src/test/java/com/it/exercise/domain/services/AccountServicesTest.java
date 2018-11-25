package com.it.exercise.domain.services;

import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.accounts.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class AccountServicesTest {
    AccountNumberGenerator accountNumberGenerator;
    @Before
    public void setup(){
        AccountServices.initialize();
        accountNumberGenerator = Mockito.mock(AccountNumberGenerator.class);
        when(accountNumberGenerator.generate()).thenReturn("10110");
    }

    @Test
    public void testSavingsAccountIsCreated() throws Exception {
        Account account= AccountServices.create(accountNumberGenerator, AccountType.SAVINGS);
        assertThat(account.getAccountType(), is(equalTo(AccountType.SAVINGS)));
        assertThat(account.getAccountNumber(), is(equalTo("10110")));
    }

    @Test
    public void testMultiCurrencyAccountIsCreated() throws Exception {
        Account account= AccountServices.create(accountNumberGenerator, AccountType.MULTICURRENCY);
        assertThat(account.getAccountType(), is(equalTo(AccountType.MULTICURRENCY)));
        assertThat(account.getAccountNumber(), is(equalTo("10110")));
        assertTrue(account.getCurrencies().containsAll(expectedCurrencies()));
    }

    private List<Currency> expectedCurrencies(){
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.getInstance("SGD"));
        currencies.add(Currency.getInstance("INR"));
        currencies.add(Currency.getInstance("JPY"));
        currencies.add(Currency.getInstance("CNY"));
        return  currencies;
    }

}
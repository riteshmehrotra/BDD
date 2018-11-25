package com.it.exercise.domain.accounts;

import com.it.exercise.domain.AccountType;
import com.it.exercise.domain.services.AccountNumberGenerator;
import com.it.exercise.domain.services.AccountServices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class SavingsAccountTest {
    AccountNumberGenerator accountNumberGenerator= Mockito.mock(AccountNumberGenerator.class);
    Account savingsAccount = AccountServices.create(accountNumberGenerator, AccountType.SAVINGS);

    @Before
    public void setup() {
        Mockito.when(accountNumberGenerator.generate()).thenReturn("102313");
    }
    @Test
    public void testFundsTransferIsApplicable() throws Exception {
        assertThat(savingsAccount.isFundsTransferApplicable(), is(equalTo(true)));
    }

    @Test
    public void testStatementGenerationIsApplicable() throws Exception {
        assertThat(savingsAccount.isStatementGenerationApplicable(), is(equalTo(true)));
    }

    @Test
    public void testOverdraftIsNotApplicable() throws Exception {
        assertThat(savingsAccount.isOverdraftApplicable(), is(equalTo(false)));
    }

    @Test
    public void testMinimumBalanceIsZero(){
        assertThat(savingsAccount.getBalance(), is(equalTo(BigDecimal.ZERO)));
    }
}
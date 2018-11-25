package com.it.exercise.domain.rules;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by TechTalks on 1/15/2018.
 */
public class SufficientBalanceRuleTest {

    @Test
    public void testSufficientBalanceExistsIfTransferAmountIsLessThanAvailableBalance(){
        BigDecimal currentBalance=new BigDecimal(100);
        BigDecimal minimumBalance=BigDecimal.TEN;
        BigDecimal amountToTransfer=new BigDecimal(20);
        SufficientBalanceRule sufficientBalanceRule = new SufficientBalanceRule(currentBalance,minimumBalance,amountToTransfer);
        assertThat(sufficientBalanceRule.isPassed(), is(equalTo(true)));
    }

    @Test
    public void testSufficientBalanceDoesNotExistIfTransferAmountIsMoreThanAvailableBalance(){
        BigDecimal currentBalance=new BigDecimal(100);
        BigDecimal minimumBalance=BigDecimal.TEN;
        BigDecimal amountToTransfer=new BigDecimal(200);
        SufficientBalanceRule sufficientBalanceRule = new SufficientBalanceRule(currentBalance,minimumBalance,amountToTransfer);
        assertThat(sufficientBalanceRule.isPassed(), is(equalTo(false)));
    }


    @Test
    public void testSufficientBalanceDoesNotExistIfTransferAmountIsEqualToAvailableBalance(){
        BigDecimal currentBalance=new BigDecimal(90);
        BigDecimal minimumBalance=BigDecimal.TEN;
        BigDecimal amountToTransfer=new BigDecimal(100);
        SufficientBalanceRule sufficientBalanceRule = new SufficientBalanceRule(currentBalance,minimumBalance,amountToTransfer);
        assertThat(sufficientBalanceRule.isPassed(), is(equalTo(false)));
    }
}
package com.it.exercise.domain.rules;

import java.math.BigDecimal;

/**
 * Created by TechTalks on 1/15/2018.
 */
public class SufficientBalanceRule implements  AccountRules {
    BigDecimal accountBalance;
    BigDecimal minimumBalance;
    BigDecimal transferAmount;

    public  SufficientBalanceRule(BigDecimal accountBalance,BigDecimal minimumBalance,BigDecimal transferAmount){
        this.accountBalance=accountBalance;
        this.minimumBalance=minimumBalance;
        this.transferAmount=transferAmount;
    }
    @Override
    public boolean isPassed() {
        return this.accountBalance.subtract(minimumBalance).compareTo(transferAmount)>0;
    }
}

package com.it.exercise.domain.facilities;

/**
 * Created by TechTalks on 1/14/2018.
 */
public interface ApplicableServices {
    boolean isFundsTransferApplicable();
    boolean isStatementGenerationApplicable();
    boolean isOverdraftApplicable();
}

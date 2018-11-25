package com.it.exercise.exceptions;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class InsufficientBalanceException extends Exception {
    public  InsufficientBalanceException(String message){
        super(message);
    }
}

package com.it.exercise.exceptions;

/**
 * Created by TechTalks on 1/14/2018.
 */
public class DepositFailedException extends  Exception{
    public  DepositFailedException(String message){
        super(message);
    }
}

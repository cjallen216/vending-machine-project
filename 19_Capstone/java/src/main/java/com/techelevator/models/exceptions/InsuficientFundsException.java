package com.techelevator.models.exceptions;

import java.math.BigDecimal;

public class InsuficientFundsException extends Exception {
	
	public InsuficientFundsException(String message)
    {
        super("Insufficient Funds!");

    }
}

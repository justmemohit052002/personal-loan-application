// LoanNotFoundException.java

package com.loanapp.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }
}
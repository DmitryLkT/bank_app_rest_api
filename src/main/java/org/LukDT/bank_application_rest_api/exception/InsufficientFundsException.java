package org.LukDT.bank_application_rest_api.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {}

    public InsufficientFundsException(String message) {
        super(message);
    }
}

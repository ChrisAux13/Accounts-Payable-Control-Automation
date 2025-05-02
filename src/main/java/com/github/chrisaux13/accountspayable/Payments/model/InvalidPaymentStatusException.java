package com.github.chrisaux13.accountspayable.Payments.model;

public class InvalidPaymentStatusException extends RuntimeException {
    public InvalidPaymentStatusException(String message) {
        super(message);
    }
}
package com.github.chrisaux13.accountspayable.Payments.model;

public class UnsupportedPaymentMethodException extends RuntimeException {
    public UnsupportedPaymentMethodException(String message) {
        super(message);
    }
}
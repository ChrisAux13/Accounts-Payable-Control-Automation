package com.github.chrisaux13.accountspayable.Payments.actions;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import com.github.chrisaux13.accountspayable.Payments.model.Payment;
import com.github.chrisaux13.accountspayable.Payments.model.PaymentMethodType;
import java.util.List;
import java.util.Map;

/**
 * Action that automatically generates payment files for different payment methods.
 * Implements the feature: System automatically generates payment files for different payment methods
 */
@Component
@RequiredArgsConstructor
public class GeneratePaymentFiles {
    
    /**
     * Executes the action to generate payment files for approved invoices.
     *
     * @param approvedPayments A list of approved payments to be processed
     * @return A map of payment method types to their corresponding generated file paths
     */
    public Map<PaymentMethodType, String> execute(List<Payment> approvedPayments) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
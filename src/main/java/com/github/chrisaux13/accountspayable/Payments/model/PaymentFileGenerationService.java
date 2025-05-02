package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaymentFileGenerationService {

    private final PaymentRepository paymentRepository;

    public Map<PaymentMethodType, String> generatePaymentFiles(List<Payment> approvedPayments) {
        validatePayments(approvedPayments);

        Map<PaymentMethodType, List<Payment>> paymentsByMethod = groupPaymentsByMethod(approvedPayments);
        Map<PaymentMethodType, String> generatedFiles = new HashMap<>();

        for (Map.Entry<PaymentMethodType, List<Payment>> entry : paymentsByMethod.entrySet()) {
            PaymentMethodType method = entry.getKey();
            List<Payment> payments = entry.getValue();
            
            String filePath;
            switch (method) {
                case BANK_TRANSFER:
                    filePath = generateNACHAFile(payments);
                    break;
                case CHECK:
                    filePath = generateCheckPrintingFile(payments);
                    break;
                case CREDIT_CARD:
                case DEBIT_CARD:
                case CASH:
                case OTHER:
                    throw new UnsupportedPaymentMethodException("Unsupported payment method: " + method);
                default:
                    throw new UnsupportedPaymentMethodException("Unknown payment method: " + method);
            }

            generatedFiles.put(method, filePath);
        }

        return generatedFiles;
    }

    private void validatePayments(List<Payment> payments) {
        for (Payment payment : payments) {
            if (payment.getStatus() != PaymentStatusType.COMPLETED) {
                throw new InvalidPaymentStatusException("Payment " + payment.getPaymentId() + " is not approved");
            }
        }
    }

    private Map<PaymentMethodType, List<Payment>> groupPaymentsByMethod(List<Payment> payments) {
        Map<PaymentMethodType, List<Payment>> grouped = new HashMap<>();
        for (Payment payment : payments) {
            grouped.computeIfAbsent(payment.getPaymentMethod(), k -> new ArrayList<>()).add(payment);
        }
        return grouped;
    }

    private String generateNACHAFile(List<Payment> achPayments) {
        return generateFile(achPayments, "NACHA", "nacha");
    }
    
    private String generateCheckPrintingFile(List<Payment> checkPayments) {
        return generateFile(checkPayments, "CheckPrinting", "pdf");
    }

    private String generateFile(List<Payment> payments, String fileType, String extension) {
        String fileName = fileType + "_" + UUID.randomUUID() + "." + extension;
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
        
        try {
            Files.createFile(filePath);
            // In a real implementation, we would write the actual file contents here
        } catch (IOException e) {
            throw new FileGenerationException("Failed to generate " + fileType + " file", e);
        }

        return filePath.toString();
    }
}
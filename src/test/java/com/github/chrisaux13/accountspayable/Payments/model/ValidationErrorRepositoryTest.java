package com.github.chrisaux13.accountspayable.Payments.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Integration tests for {@link ValidationErrorRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ValidationErrorRepositoryTest {

    @Autowired
    private ValidationErrorRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a test Payment
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethodType.CHECK);
        payment.setAmount(new java.math.BigDecimal("100.00"));
        payment.setStatus(PaymentStatusType.PENDING);
        Payment savedPayment = paymentRepository.save(payment);

        // Create a test ValidationError
        ValidationError validationError = new ValidationError();
        validationError.setPayment(savedPayment);
        validationError.setErrorType("TEST_ERROR");
        validationError.setErrorMessage("This is a test error message");

        // Save the ValidationError
        ValidationError savedValidationError = repository.save(validationError);

        // Verify the ValidationError was assigned an ID
        assertThat(savedValidationError.getErrorId()).isNotNull();

        // Retrieve the ValidationError from the database
        ValidationError retrievedValidationError = repository.findById(savedValidationError.getErrorId()).orElse(null);

        // Verify the ValidationError was retrieved and has correct properties
        assertThat(retrievedValidationError)
            .isNotNull()
            .satisfies(error -> {
                assertThat(error.getErrorId()).isEqualTo(savedValidationError.getErrorId());
                assertThat(error.getErrorType()).isEqualTo("TEST_ERROR");
                assertThat(error.getErrorMessage()).isEqualTo("This is a test error message");
                assertThat(error.getCreatedAt()).isNotNull();
                assertThat(error.getPayment().getPaymentId()).isEqualTo(savedPayment.getPaymentId());
            });
    }
}
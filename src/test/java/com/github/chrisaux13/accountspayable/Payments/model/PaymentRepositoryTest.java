package com.github.chrisaux13.accountspayable.Payments.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Integration tests for {@link PaymentRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a test vendor
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendorRepository.save(vendor);

        // Create a test payment
        Payment payment = new Payment();
        payment.setVendor(vendor);
        payment.setPaymentMethod(PaymentMethodType.BANK_TRANSFER);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setStatus(PaymentStatusType.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        // Save the payment
        Payment savedPayment = repository.save(payment);

        // Verify the payment was assigned an ID
        assertThat(savedPayment.getPaymentId()).isNotNull();

        // Retrieve the payment from the database
        Payment retrievedPayment = repository.findById(savedPayment.getPaymentId()).orElse(null);

        // Verify the payment was retrieved and has correct properties
        assertThat(retrievedPayment)
            .isNotNull()
            .satisfies(p -> {
                assertThat(p.getPaymentId()).isEqualTo(savedPayment.getPaymentId());
                assertThat(p.getVendor().getVendorName()).isEqualTo("Test Vendor");
                assertThat(p.getPaymentMethod()).isEqualTo(PaymentMethodType.BANK_TRANSFER);
                assertThat(p.getAmount()).isEqualByComparingTo(new BigDecimal("100.00"));
                assertThat(p.getStatus()).isEqualTo(PaymentStatusType.PENDING);
                assertThat(p.getCreatedAt()).isNotNull();
                assertThat(p.getUpdatedAt()).isNotNull();
            });
    }
}
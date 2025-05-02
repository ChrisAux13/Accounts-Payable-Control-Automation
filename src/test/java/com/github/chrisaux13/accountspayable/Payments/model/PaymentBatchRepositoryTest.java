package com.github.chrisaux13.accountspayable.Payments.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Integration tests for {@link PaymentBatchRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PaymentBatchRepositoryTest {

    @Autowired
    private PaymentBatchRepository repository;
    
    @Test
    public void testSaveAndFindById() {
        // Create a test payment batch
        PaymentBatch paymentBatch = new PaymentBatch();
        paymentBatch.setCreatedBy(UUID.randomUUID());
        paymentBatch.setStatus(PaymentStatusType.PENDING);
        paymentBatch.setPaymentDate(LocalDate.now());
        paymentBatch.setIsRecurring(false);
        
        // Save the payment batch
        PaymentBatch savedPaymentBatch = repository.save(paymentBatch);
        
        // Verify the payment batch was assigned an ID
        assertThat(savedPaymentBatch.getBatchId()).isNotNull();
        
        // Retrieve the payment batch from the database
        PaymentBatch retrievedPaymentBatch = repository.findById(savedPaymentBatch.getBatchId()).orElse(null);
        
        // Verify the payment batch was retrieved and has correct properties
        assertThat(retrievedPaymentBatch)
            .isNotNull()
            .satisfies(batch -> {
                assertThat(batch.getBatchId()).isEqualTo(savedPaymentBatch.getBatchId());
                assertThat(batch.getCreatedBy()).isEqualTo(savedPaymentBatch.getCreatedBy());
                assertThat(batch.getStatus()).isEqualTo(PaymentStatusType.PENDING);
                assertThat(batch.getPaymentDate()).isEqualTo(savedPaymentBatch.getPaymentDate());
                assertThat(batch.getIsRecurring()).isFalse();
                assertThat(batch.getCreatedAt()).isNotNull();
                assertThat(batch.getUpdatedAt()).isNotNull();
            });
    }
}
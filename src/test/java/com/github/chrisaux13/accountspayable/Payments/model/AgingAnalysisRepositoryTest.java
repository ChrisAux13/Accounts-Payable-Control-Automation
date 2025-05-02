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
 * Integration tests for {@link AgingAnalysisRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AgingAnalysisRepositoryTest {

    @Autowired
    private AgingAnalysisRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a test payment
        Payment testPayment = new Payment();
        testPayment.setAmount(new java.math.BigDecimal("100.00"));
        testPayment.setPaymentMethod(PaymentMethodType.CREDIT_CARD);
        testPayment.setStatus(PaymentStatusType.PENDING);
        Payment savedPayment = paymentRepository.save(testPayment);

        // Create a test aging analysis
        AgingAnalysis agingAnalysis = new AgingAnalysis();
        agingAnalysis.setPayment(savedPayment);
        agingAnalysis.setDaysOutstanding(30);
        agingAnalysis.setAgingCategory("30-60 days");

        // Save the aging analysis
        AgingAnalysis savedAgingAnalysis = repository.save(agingAnalysis);

        // Verify the aging analysis was assigned an ID
        assertThat(savedAgingAnalysis.getAnalysisId()).isNotNull();

        // Retrieve the aging analysis from the database
        AgingAnalysis retrievedAgingAnalysis = repository.findById(savedAgingAnalysis.getAnalysisId()).orElse(null);

        // Verify the aging analysis was retrieved and has correct properties
        assertThat(retrievedAgingAnalysis)
            .isNotNull()
            .satisfies(analysis -> {
                assertThat(analysis.getAnalysisId()).isEqualTo(savedAgingAnalysis.getAnalysisId());
                assertThat(analysis.getPayment().getPaymentId()).isEqualTo(savedPayment.getPaymentId());
                assertThat(analysis.getDaysOutstanding()).isEqualTo(30);
                assertThat(analysis.getAgingCategory()).isEqualTo("30-60 days");
                assertThat(analysis.getCreatedAt()).isNotNull();
            });
    }
}
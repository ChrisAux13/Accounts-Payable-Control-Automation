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
 * Integration tests for {@link ApprovalWorkflowRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ApprovalWorkflowRepositoryTest {

    @Autowired
    private ApprovalWorkflowRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a test payment
        final Payment payment = new Payment();
        payment.setAmount(new BigDecimal("100.00"));
        final Payment savedPayment = paymentRepository.save(payment);

        // Create a test approval workflow
        ApprovalWorkflow workflow = new ApprovalWorkflow();
        workflow.setPayment(payment);
        workflow.setApproverId(UUID.randomUUID());
        workflow.setStatus(PaymentStatusType.PENDING);
        workflow.setComments("Test comment");

        // Save the approval workflow
        ApprovalWorkflow savedWorkflow = repository.save(workflow);

        // Verify the workflow was assigned an ID
        assertThat(savedWorkflow.getWorkflowId()).isNotNull();

        // Retrieve the workflow from the database
        final ApprovalWorkflow retrievedWorkflow = repository.findById(savedWorkflow.getWorkflowId()).orElse(null);
        final UUID expectedPaymentId = savedPayment.getPaymentId();

        // Verify the workflow was retrieved and has correct properties
        assertThat(retrievedWorkflow)
            .isNotNull()
            .satisfies(w -> {
                assertThat(w.getWorkflowId()).isEqualTo(savedWorkflow.getWorkflowId());
                assertThat(w.getPayment().getPaymentId()).isEqualTo(expectedPaymentId);
                assertThat(w.getApproverId()).isEqualTo(workflow.getApproverId());
                assertThat(w.getStatus()).isEqualTo(PaymentStatusType.PENDING);
                assertThat(w.getComments()).isEqualTo("Test comment");
                assertThat(w.getCreatedAt()).isNotNull();
            });
    }
}
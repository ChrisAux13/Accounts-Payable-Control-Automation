package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link ApprovalWorkflow} entities.
 */
public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, UUID> {
}
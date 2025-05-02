package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link PaymentBatch} entities.
 */
public interface PaymentBatchRepository extends JpaRepository<PaymentBatch, UUID> {
}
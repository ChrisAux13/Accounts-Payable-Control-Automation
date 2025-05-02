package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link Payment} entities.
 */
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link ValidationError} entities.
 */
public interface ValidationErrorRepository extends JpaRepository<ValidationError, UUID> {
}
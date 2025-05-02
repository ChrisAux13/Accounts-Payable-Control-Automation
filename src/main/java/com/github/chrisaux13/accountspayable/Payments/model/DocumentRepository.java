package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link Document} entities.
 */
public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
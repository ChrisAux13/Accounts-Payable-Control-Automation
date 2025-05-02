package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link AgingAnalysis} entities.
 */
public interface AgingAnalysisRepository extends JpaRepository<AgingAnalysis, UUID> {
}
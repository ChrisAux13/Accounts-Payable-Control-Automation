package com.github.chrisaux13.accountspayable.Payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an aging analysis in the system.
 */
@Entity
@Table(name = "aging_analysis")
@Data
public class AgingAnalysis {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "analysis_id", updatable = false, nullable = false)
    private UUID analysisId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @NotNull
    @Column(name = "days_outstanding", nullable = false)
    private Integer daysOutstanding;

    @NotNull
    @Size(max = 20)
    @Column(name = "aging_category", nullable = false, length = 20)
    private String agingCategory;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
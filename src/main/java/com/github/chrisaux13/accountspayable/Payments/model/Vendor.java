package com.github.chrisaux13.accountspayable.Payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a vendor in the system.
 */
@Entity
@Table(name = "vendors")
@Data
public class Vendor {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "vendor_id", updatable = false, nullable = false)
    private UUID vendorId;

    @NotNull
    @Size(max = 100)
    @Column(name = "vendor_name", nullable = false, length = 100)
    private String vendorName;

    @Size(max = 50)
    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;

    @Size(max = 50)
    @Column(name = "bank_routing_number", length = 50)
    private String bankRoutingNumber;

    @Size(max = 20)
    @Column(name = "swift_code", length = 20)
    private String swiftCode;

    @Column(name = "is_blocked")
    private Boolean isBlocked = false;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
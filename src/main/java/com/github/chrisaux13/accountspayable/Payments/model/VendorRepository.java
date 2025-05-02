package com.github.chrisaux13.accountspayable.Payments.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository for {@link Vendor} entities.
 */
public interface VendorRepository extends JpaRepository<Vendor, UUID> {
}
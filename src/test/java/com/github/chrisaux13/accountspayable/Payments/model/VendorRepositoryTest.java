package com.github.chrisaux13.accountspayable.Payments.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Integration tests for {@link VendorRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class VendorRepositoryTest {

    @Autowired
    private VendorRepository repository;
    
    @Test
    public void testSaveAndFindById() {
        // Create a test vendor
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendor.setBankAccountNumber("1234567890");
        vendor.setBankRoutingNumber("987654321");
        vendor.setSwiftCode("TESTSWIFT");
        vendor.setIsBlocked(false);
        
        // Save the vendor
        Vendor savedVendor = repository.save(vendor);
        
        // Verify the vendor was assigned an ID
        assertThat(savedVendor.getVendorId()).isNotNull();
        
        // Retrieve the vendor from the database
        Vendor retrievedVendor = repository.findById(savedVendor.getVendorId()).orElse(null);
        
        // Verify the vendor was retrieved and has correct properties
        assertThat(retrievedVendor)
            .isNotNull()
            .satisfies(v -> {
                assertThat(v.getVendorId()).isEqualTo(savedVendor.getVendorId());
                assertThat(v.getVendorName()).isEqualTo("Test Vendor");
                assertThat(v.getBankAccountNumber()).isEqualTo("1234567890");
                assertThat(v.getBankRoutingNumber()).isEqualTo("987654321");
                assertThat(v.getSwiftCode()).isEqualTo("TESTSWIFT");
                assertThat(v.getIsBlocked()).isFalse();
                assertThat(v.getCreatedAt()).isNotNull();
                assertThat(v.getUpdatedAt()).isNotNull();
            });
    }
}
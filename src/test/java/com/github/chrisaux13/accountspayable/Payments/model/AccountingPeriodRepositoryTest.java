package com.github.chrisaux13.accountspayable.Payments.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

/**
 * Integration tests for {@link AccountingPeriodRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountingPeriodRepositoryTest {

    @Autowired
    private AccountingPeriodRepository repository;
    
    @Test
    public void testSaveAndFindById() {
        // Create a test accounting period
        AccountingPeriod period = new AccountingPeriod();
        period.setStartDate(LocalDate.of(2023, 1, 1));
        period.setEndDate(LocalDate.of(2023, 1, 31));
        period.setIsOpen(true);
        
        // Save the accounting period
        AccountingPeriod savedPeriod = repository.save(period);
        
        // Verify the accounting period was assigned an ID
        assertThat(savedPeriod.getPeriodId()).isNotNull();
        
        // Retrieve the accounting period from the database
        AccountingPeriod retrievedPeriod = repository.findById(savedPeriod.getPeriodId()).orElse(null);
        
        // Verify the accounting period was retrieved and has correct properties
        assertThat(retrievedPeriod)
            .isNotNull()
            .satisfies(p -> {
                assertThat(p.getPeriodId()).isEqualTo(savedPeriod.getPeriodId());
                assertThat(p.getStartDate()).isEqualTo(LocalDate.of(2023, 1, 1));
                assertThat(p.getEndDate()).isEqualTo(LocalDate.of(2023, 1, 31));
                assertThat(p.getIsOpen()).isTrue();
                assertThat(p.getCreatedAt()).isNotNull();
            });
    }
}
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
 * Integration tests for {@link AuditLogRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuditLogRepositoryTest {

    @Autowired
    private AuditLogRepository repository;
    
    @Test
    public void testSaveAndFindById() {
        // Create a test audit log
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("TestEntity");
        auditLog.setEntityId(UUID.randomUUID());
        auditLog.setAction("CREATE");
        auditLog.setActorId(UUID.randomUUID());
        auditLog.setDetails("{\"key\": \"value\"}");
        
        // Save the audit log
        AuditLog savedAuditLog = repository.save(auditLog);
        
        // Verify the audit log was assigned an ID
        assertThat(savedAuditLog.getLogId()).isNotNull();
        
        // Retrieve the audit log from the database
        AuditLog retrievedAuditLog = repository.findById(savedAuditLog.getLogId()).orElse(null);
        
        // Verify the audit log was retrieved and has correct properties
        assertThat(retrievedAuditLog)
            .isNotNull()
            .satisfies(log -> {
                assertThat(log.getLogId()).isEqualTo(savedAuditLog.getLogId());
                assertThat(log.getEntityType()).isEqualTo("TestEntity");
                assertThat(log.getEntityId()).isEqualTo(savedAuditLog.getEntityId());
                assertThat(log.getAction()).isEqualTo("CREATE");
                assertThat(log.getActorId()).isEqualTo(savedAuditLog.getActorId());
                assertThat(log.getDetails()).isEqualTo("{\"key\": \"value\"}");
                assertThat(log.getCreatedAt()).isNotNull();
            });
    }
}
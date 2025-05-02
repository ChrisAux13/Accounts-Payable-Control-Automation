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
 * Integration tests for {@link DocumentRepository}.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository repository;
    
    @Test
    public void testSaveAndFindById() {
        // Create a test document
        Document document = new Document();
        document.setDocumentType(DocumentType.INVOICE);
        document.setFilePath("/path/to/invoice.pdf");
        document.setUploadedBy(UUID.randomUUID());
        
        // Save the document
        Document savedDocument = repository.save(document);
        
        // Verify the document was assigned an ID
        assertThat(savedDocument.getDocumentId()).isNotNull();
        
        // Retrieve the document from the database
        Document retrievedDocument = repository.findById(savedDocument.getDocumentId()).orElse(null);
        
        // Verify the document was retrieved and has correct properties
        assertThat(retrievedDocument)
            .isNotNull()
            .satisfies(doc -> {
                assertThat(doc.getDocumentId()).isEqualTo(savedDocument.getDocumentId());
                assertThat(doc.getDocumentType()).isEqualTo(DocumentType.INVOICE);
                assertThat(doc.getFilePath()).isEqualTo("/path/to/invoice.pdf");
                assertThat(doc.getUploadedBy()).isEqualTo(savedDocument.getUploadedBy());
                assertThat(doc.getCreatedAt()).isNotNull();
            });
    }
}
package com.example.api.service;

import com.example.api.model.AuthorizationRepository;
import com.example.api.model.Document;
import com.example.api.model.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.BDDAssertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;


@SpringBootTest
@TestPropertySource(properties = {
    "openfga.enabled=false"
})
public class DocumentServiceTest {


    private final DocumentService documentService;

    @MockBean
    private AuthorizationRepository authorizationRepository;

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceTest(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    // Avoid entity without permission
    @Test
    public void testDualWriteFailure() {
        willThrow(new RuntimeException("FGA Error")).given(authorizationRepository).save(any(Document.class));

        // Test the save method
        Document document = new Document();
        document.setId(4l);
        document.setName("test-focument");
        document.setOwnerId("test-owner");

        try {
            documentService.save(document);
            fail("Should have thrown an exception");
        } catch (DocumentServiceException e) {
            assertEquals("Unexpected error", e.getMessage());

            documentRepository.findById(4l).ifPresentOrElse(
                (doc) -> fail("Document should not have been saved"),
                () -> {});
        }
    }

}

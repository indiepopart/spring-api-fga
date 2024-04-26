package com.example.api.service;

import com.example.api.model.AuthorizationRepository;
import com.example.api.model.Document;
import com.example.api.model.DocumentRepository;
import dev.openfga.OpenFga;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.BDDAssertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;


@SpringBootTest
@ActiveProfiles("test")
public class DocumentServiceTest {


    private final DocumentService documentService;

    @MockBean
    private AuthorizationRepository authorizationRepository;

    private DocumentRepository documentRepository;

    @MockBean
    private OpenFga openFga;

    @Autowired
    public DocumentServiceTest(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    // Avoid entity without permission
    @Test
    @WithMockUser(username = "test-user")
    public void testDualWriteFailure() {
        willThrow(new RuntimeException("FGA Error")).given(authorizationRepository).save(any(Document.class));
        willReturn(true).given(openFga).check(any(), any(), any(), any());

        // Test the save method
        Document document = new Document();
        document.setId(4l);
        document.setName("test-focument");
        document.setOwnerId("test-owner");

        assertThatExceptionOfType(DocumentServiceException.class).isThrownBy(() -> {
            documentService.save(document);
        }).withMessage("Unexpected error");


        documentRepository.findById(4l).ifPresentOrElse(
            (doc) -> fail("Document should not have been saved"),
            () -> {});
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testSaveNotAuthorized(){

        willReturn(false).given(openFga).check(any(), any(), any(), any());

        Document document = new Document();
        document.setId(4l);
        document.setName("test-document");
        document.setOwnerId("test-user");
        document.setParentId(5L);


        assertThatExceptionOfType(AccessDeniedException.class).isThrownBy(() -> {documentService.save(document);})
                .withMessage("Access is denied");


    }
}

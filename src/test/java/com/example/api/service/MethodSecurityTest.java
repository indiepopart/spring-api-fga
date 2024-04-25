package com.example.api.service;

import com.example.api.model.Document;
import dev.openfga.OpenFga;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;


@SpringBootTest
@TestPropertySource(properties = {
        "openfga.enabled=false"
})
public class MethodSecurityTest {
    // Requires openfga server or mock OpenFga bean

    @MockBean
    private OpenFga openFga;

    @Autowired
    private DocumentService documentService;

    @Test
    @WithMockUser(username = "test-user")
    public void testSave(){

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

package com.example.demo.web;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import com.example.demo.service.DocumentServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateDocument() throws Exception {

        Document document = new Document();
        document.setName("test-doc");
        document.setDescription("test-description");

        willReturn(document).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/document").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test-doc\",\"description\":\"test-description\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.name").value("test-doc"))
            .andExpect(jsonPath("$.description").value("test-description"));
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateDocumentError() throws Exception {

        willThrow(new DocumentServiceException(new RuntimeException("FGA Error"))).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/document").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-doc\",\"description\":\"test-description\"}"))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateDocumentForbidden() throws Exception {
        Document document = new Document();
        document.setName("test-doc");
        document.setDescription("test-description");
        document.setParentId(1L);

        // AccessDeniedException thrown when @PreAuthorize rejects the operation
        willThrow(new AccessDeniedException("Access is denied")).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/document").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-doc\",\"description\":\"test-description\"}"))
                .andExpect(status().isForbidden());

    }
}

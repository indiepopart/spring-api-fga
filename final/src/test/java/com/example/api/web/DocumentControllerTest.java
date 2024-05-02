package com.example.api.web;

import com.example.api.model.Document;
import com.example.api.service.DocumentService;
import com.example.api.service.DocumentServiceException;
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
    public void testCreateFile() throws Exception {

        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");

        willReturn(document).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/file").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test-file\",\"description\":\"test-description\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.name").value("test-file"))
            .andExpect(jsonPath("$.description").value("test-description"));
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFileError() throws Exception {

        willThrow(new DocumentServiceException(new RuntimeException("FGA Error"))).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/file").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-file\",\"description\":\"test-description\"}"))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFileForbidden() throws Exception {
        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");
        document.setParentId(1L);

        // AccessDeniedException thrown when @PreAuthorize rejects the operation
        willThrow(new AccessDeniedException("Access is denied")).given(documentService).save(any(Document.class));

        mockMvc.perform(post("/file").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-file\",\"description\":\"test-description\"}"))
                .andExpect(status().isForbidden());

    }
}

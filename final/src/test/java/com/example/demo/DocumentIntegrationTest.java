package com.example.demo;

import com.example.demo.model.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.openfga.OpenFGAContainer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
public class DocumentIntegrationTest {

    @Container
    static OpenFGAContainer openfga = new OpenFGAContainer("openfga/openfga:v1.4.3");
    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void registerOpenFGAProperties(DynamicPropertyRegistry registry) {
        registry.add("openfga.api-url", () -> "http://localhost:" + openfga.getMappedPort(8080));
    }

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFileIsFobidden() throws Exception {

        Document document = new Document();
        document.setParentId(1L);
        document.setName("test-file");
        document.setDescription("test-description");

        mockMvc.perform(post("/document").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFile() throws Exception {

        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");

        MvcResult mvcResult = mockMvc.perform(post("/document")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("test-description"))
                .andReturn();

        Document result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);

        mockMvc.perform(get("/document/{id}", result.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("test-description"));
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testUpdateFile() throws Exception {
        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");

        MvcResult mvcResult = mockMvc.perform(post("/document")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("test-description"))
                .andReturn();

        Document result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);
        document.setDescription("updated-description");


        mockMvc.perform(put("/document/{id}", result.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("updated-description"));

        mockMvc.perform(get("/document/{id}", result.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("updated-description"));
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testDeleteFile() throws Exception {
        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");

        MvcResult mvcResult = mockMvc.perform(post("/document").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("test-description"))
                .andReturn();

        Document result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);

        mockMvc.perform(delete("/document/{id}", result.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testDeleteFile_NotOwned_AccessDenied() throws Exception {
        Document document = new Document();
        document.setName("test-file");
        document.setDescription("test-description");

        MvcResult mvcResult = mockMvc.perform(post("/document")
                        .with(csrf())
                        .with(user("owner-user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(document)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("test-file"))
                .andExpect(jsonPath("$.description").value("test-description"))
                .andReturn();

        Document result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);

        mockMvc.perform(delete("/document/{id}", result.getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }
}

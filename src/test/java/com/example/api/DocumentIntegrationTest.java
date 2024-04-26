package com.example.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.openfga.OpenFGAContainer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
public class DocumentIntegrationTest {

    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;

    @Container
    static OpenFGAContainer openfga = new OpenFGAContainer("openfga/openfga:v1.4.3");

    @DynamicPropertySource
    static void reegisterOpenFGAProperties(DynamicPropertyRegistry registry){
        registry.add("openfga.api-url", () -> "http://localhost:" + openfga.getMappedPort(8080));
    }

    @BeforeEach
    public void  init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFile() throws Exception {

        mockMvc.perform(post("/file").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-file\",\"description\":\"test-description\", \"parentId\": 1}"))
                .andExpect(status().isForbidden());
    }

}

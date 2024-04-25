package com.example.api;

import dev.openfga.OpenFga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = {
        "openfga.enabled=false"
})
public class DocumentIntegrationTest {

    // Start test container and initialize

    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;

    @MockBean
    private OpenFga openFga;


    @BeforeEach
    public void  init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "test-user")
    public void testCreateFile() throws Exception {

        willReturn(false).given(openFga).check(any(), any(), any(), any());

        mockMvc.perform(post("/file").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test-file\",\"description\":\"test-description\", \"parentId\": 1}"))
                .andExpect(status().isForbidden());
    }

}

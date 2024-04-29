package com.example.api;

import com.example.api.model.Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ObjectMapperTest {


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testDocumentWrite() throws JsonProcessingException {
        Document document = new Document();
        document.setParentId(1L);
        document.setName("test-file");
        document.setDescription("test-description");

        System.out.println(objectMapper.writeValueAsString(document));
    }
}

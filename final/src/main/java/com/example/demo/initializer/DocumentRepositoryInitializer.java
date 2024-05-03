package com.example.demo.initializer;

import com.example.demo.model.Document;
import com.example.demo.model.DocumentBuilder;
import com.example.demo.model.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
class DocumentRepositoryInitializer implements CommandLineRunner {


    private DocumentRepository documentRepository;

    public DocumentRepositoryInitializer(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add some initial data
        LocalDateTime dateTime = LocalDateTime.now();

        Document document1 = new DocumentBuilder()
                .withOwnerId("test-user")
                .withName("planning-v0")
                .withDescription("Planning doc")
                .withCreatedTime(dateTime)
                .withModifiedTime(dateTime)
                .withFileExtension(".doc")
                .build();

        Document document2 = new DocumentBuilder()
                .withOwnerId("test-user")
                .withName("image-1")
                .withDescription("Some image")
                .withCreatedTime(dateTime)
                .withModifiedTime(dateTime)
                .withFileExtension(".jpg")
                .build();

        Document document3 = new DocumentBuilder()
                .withOwnerId("test-user")
                .withName("meeting-notes")
                .withDescription("Some text file")
                .withCreatedTime(dateTime)
                .withModifiedTime(dateTime)
                .withFileExtension(".txt")
                .build();

        documentRepository.save(document1);
        documentRepository.save(document2);
        documentRepository.save(document3);
    }
}

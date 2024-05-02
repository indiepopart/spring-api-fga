package com.example.demo.initializer;

import com.example.demo.model.Document;
import com.example.demo.model.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DocumentRepositoryInitializer implements CommandLineRunner {


    private DocumentRepository documentRepository;

    public DocumentRepositoryInitializer(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add some initial data
         documentRepository.save(new Document(null, "test-user", "file1", "description1", "createdTime1", "modifiedTime1", "quotaBytesUsed1", "version1", "originalFilename1", "fileExtension1"));
         documentRepository.save(new Document(null, "test-user", "file2", "description2", "createdTime2", "modifiedTime2", "quotaBytesUsed2", "version2", "originalFilename2", "fileExtension2"));
         documentRepository.save(new Document(null, "test-user", "file3", "description3", "createdTime3", "modifiedTime3", "quotaBytesUsed3", "version3", "originalFilename3", "fileExtension3"));
    }
}

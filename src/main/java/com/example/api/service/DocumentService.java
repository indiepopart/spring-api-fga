package com.example.api.service;

import com.example.api.model.AuthorizationRepository;
import com.example.api.model.Document;
import com.example.api.model.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    private AuthorizationRepository authorizationRepository;

    public DocumentService(DocumentRepository documentRepository, AuthorizationRepository authorizationRepository) {
        this.documentRepository = documentRepository;
        this.authorizationRepository = authorizationRepository;
    }
    @Transactional
    public Document save(Document file) {
        try {
            Document result = documentRepository.save(file);
            authorizationRepository.save(result);
            return result;
        } catch(Exception e){
            throw new DocumentServiceException("Unexpected error", e);
        }
    }
}
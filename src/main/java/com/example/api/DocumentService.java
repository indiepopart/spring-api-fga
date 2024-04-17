package com.example.api;

import com.example.api.model.AuthorizationRepository;
import com.example.api.model.Document;
import com.example.api.model.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    private AuthorizationRepository authorizationRepository;

    public DocumentService(DocumentRepository documentRepository, AuthorizationRepository authorizationRepository) {
        this.documentRepository = documentRepository;
        this.authorizationRepository = authorizationRepository;
    }

    public Document save(Document file){
        Document result = documentRepository.save(file);
        authorizationRepository.save(result);
        return result;
    }
}

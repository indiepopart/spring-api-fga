package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.model.DocumentRepository;
import com.example.demo.model.Permission;
import com.example.demo.model.PermissionBuilder;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DocumentService {

    private static Logger logger = LoggerFactory.getLogger(DocumentService.class.getName());

    private DocumentRepository documentRepository;

    private AuthorizationService authorizationService;

    public DocumentService(DocumentRepository documentRepository, AuthorizationService authorizationService) {
        this.documentRepository = documentRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    @PreAuthorize("#document.parentId == null or @fga.check('document', #document.parentId, 'writer', 'user')")
    public Document save(@P("document") Document document) {
        try {
            Document result = documentRepository.save(document);
            Permission permission = new PermissionBuilder()
                    .withDocumentId(result.getId())
                    .withRelation("owner")
                    .withUserId(result.getOwnerId())
                    .build();
            authorizationService.create(permission);
            return result;
        } catch(Exception e){
            logger.error("Error saving document", e);
            throw new DocumentServiceException("Unexpected error", e);
        }
    }

    @PreAuthorize("@fga.check('document', #id, 'viewer', 'user')")
    public Optional<Document> findById(@P("id") Long id) {
        return documentRepository.findById(id);
    }

    @PreAuthorize("@fga.check('document', #id, 'owner', 'user')")
    public void deleteById(@P("id") Long id) {
        documentRepository.deleteById(id);
    }

    @PreAuthorize("@fga.check('document', #document.id, 'writer', 'user')")
    public Document update(@P("document") Document document){
        return documentRepository.save(document);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }
}

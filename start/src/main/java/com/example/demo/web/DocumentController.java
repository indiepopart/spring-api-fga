package com.example.demo.web;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import com.example.demo.service.DocumentServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// Metadata only requests
@RestController
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/document")
    public Document createDocument(@RequestBody Document document, Principal principal) {
        document.setOwnerId(principal.getName());
        return documentService.save(document);
    }

    @GetMapping("/document")
    public List<Document> getDocuments() {
        return documentService.findAll();
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Long id) {
        return documentService.findById(id).map(document -> ResponseEntity.ok().body(document))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/document/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document document) {
        // TODO this will return access denied if document does not exist
        return documentService.findById(id).map(update -> {
            update.setName(document.getName());
            update.setDescription(document.getDescription());
            update.setCreatedTime(document.getCreatedTime());
            update.setModifiedTime(document.getModifiedTime());
            update.setQuotaBytesUsed(document.getQuotaBytesUsed());
            update.setVersion(document.getVersion());
            update.setOriginalFilename(document.getOriginalFilename());
            update.setFileExtension(document.getFileExtension());
            Document result = documentService.update(update);
            return ResponseEntity.ok().body(result);
        }).orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(DocumentServiceException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }


}

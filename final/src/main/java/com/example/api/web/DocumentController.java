package com.example.api.web;

import com.example.api.model.Document;
import com.example.api.service.DocumentService;
import com.example.api.service.DocumentServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.parameters.P;
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

    @PostMapping("/file")
    //@PreAuthorize("#document.parentId == null or @fga.check('document', #document.parentId, 'writer', 'user')")
    public Document createFile(@RequestBody @P("document") Document file, Principal principal) {
        file.setOwnerId(principal.getName());
        return documentService.save(file);
    }

    @GetMapping("/file")
    public List<Document> getFiles() {
        return documentService.findAll();
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Document> getFile(@PathVariable @P("id") Long id) {
        return documentService.findById(id).map(file -> ResponseEntity.ok().body(file))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        documentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/file/{id}")
    public ResponseEntity<Document> updateFile(@PathVariable Long id, @RequestBody Document file) {
        // TODO this will return access denied if document does not exist
        return documentService.findById(id).map(update -> {
            update.setName(file.getName());
            update.setDescription(file.getDescription());
            update.setCreatedTime(file.getCreatedTime());
            update.setModifiedTime(file.getModifiedTime());
            update.setQuotaBytesUsed(file.getQuotaBytesUsed());
            update.setVersion(file.getVersion());
            update.setOriginalFilename(file.getOriginalFilename());
            update.setFileExtension(file.getFileExtension());
            Document result = documentService.update(update);
            return ResponseEntity.ok().body(result);
        }).orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(DocumentServiceException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    public ResponseEntity<String> handle(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }

}

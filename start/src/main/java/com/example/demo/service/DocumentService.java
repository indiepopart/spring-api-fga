package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.model.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document save(Document file) {
        return documentRepository.save(file);
    }

    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }


    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }


    public Document update(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }
}

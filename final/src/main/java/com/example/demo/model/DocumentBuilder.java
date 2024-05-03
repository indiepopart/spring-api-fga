package com.example.demo.model;

import java.time.LocalDateTime;

public class DocumentBuilder {

    private Document document;
    public DocumentBuilder() {
        document = new Document();
    }

    public DocumentBuilder withId(Long id) {
        document.setId(id);
        return this;
    }

    public DocumentBuilder withOwnerId(String ownerId) {
        document.setOwnerId(ownerId);
        return this;
    }

    public DocumentBuilder withName(String name) {
        document.setName(name);
        return this;
    }

    public DocumentBuilder withDescription(String description) {
        document.setDescription(description);
        return this;
    }

    public DocumentBuilder withCreatedTime(LocalDateTime localDateTime) {
        document.setCreatedTime(localDateTime.toString());
        return this;
    }

    public DocumentBuilder withModifiedTime(LocalDateTime modifiedTime) {
        document.setModifiedTime(modifiedTime.toString());
        return this;
    }

    public DocumentBuilder withQuotaBytesUsed(String quotaBytesUsed) {
        document.setQuotaBytesUsed(quotaBytesUsed);
        return this;
    }

    public DocumentBuilder withVersion(String version) {
        document.setVersion(version);
        return this;
    }

    public DocumentBuilder withOriginalFilename(String originalFilename) {
        document.setOriginalFilename(originalFilename);
        return this;
    }

    public DocumentBuilder withFileExtension(String fileExtension) {
        document.setFileExtension(fileExtension);
        return this;
    }

    public Document build() {
        return document;
    }
}

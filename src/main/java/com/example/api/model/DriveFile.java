package com.example.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class DriveFile {

    @Id
    private String id;
    @NotEmpty
    private String name;
    private String description;
    private String createdTime;
    private String modifiedTime;
    private String quotaBytesUsed;
    private String version;
    private String originalFilename;
    private String fileExtension;

    public DriveFile() {
    }

    public DriveFile(String id, String name, String description, String createdTime, String modifiedTime, String quotaBytesUsed, String version, String originalFilename, String fileExtension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.quotaBytesUsed = quotaBytesUsed;
        this.version = version;
        this.originalFilename = originalFilename;
        this.fileExtension = fileExtension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getQuotaBytesUsed() {
        return quotaBytesUsed;
    }

    public void setQuotaBytesUsed(String quotaBytesUsed) {
        this.quotaBytesUsed = quotaBytesUsed;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}

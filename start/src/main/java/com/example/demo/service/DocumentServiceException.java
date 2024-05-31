package com.example.demo.service;

public class DocumentServiceException extends RuntimeException {

    public DocumentServiceException(Exception e) {
        super(e);
    }

    public DocumentServiceException(String message, Exception e) {
        super(message, e);
    }
}

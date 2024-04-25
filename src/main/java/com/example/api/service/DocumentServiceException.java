package com.example.api.service;

public class DocumentServiceException extends RuntimeException {

    public DocumentServiceException(Exception e) {
        super(e);
    }

    public DocumentServiceException(String unexpectedError, Exception e) {
        super(unexpectedError, e);
    }
}

package com.example.api.service;

public class AuthorizationServiceException extends RuntimeException {
    public AuthorizationServiceException(Exception e) {
        super(e);
    }
}

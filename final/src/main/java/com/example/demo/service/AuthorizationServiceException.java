package com.example.demo.service;

public class AuthorizationServiceException extends RuntimeException {
    public AuthorizationServiceException(Exception e) {
        super(e);
    }
}

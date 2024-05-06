package com.example.demo.web;

import com.example.demo.model.Permission;
import com.example.demo.service.AuthorizationService;
import com.example.demo.service.AuthorizationServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PermissionController {

    private AuthorizationService authorizationService;

    public PermissionController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/permission")
    @PreAuthorize("@fga.check('document', #permission.documentId, 'owner', 'user')")
    public void createPermission(@P("permission") @RequestBody Permission permission) {
        authorizationService.create(permission);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(AuthorizationServiceException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

}

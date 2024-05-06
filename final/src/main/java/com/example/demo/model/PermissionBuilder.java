package com.example.demo.model;

public class PermissionBuilder {

    private Permission permission;

    public PermissionBuilder() {
        permission = new Permission();
    }

    public PermissionBuilder withUserId(String userId) {
        permission.setUserId(userId);
        return this;
    }

    public PermissionBuilder withRelation(String relation) {
        permission.setRelation(relation);
        return this;
    }

    public PermissionBuilder withDocumentId(Long documentId) {
        permission.setDocumentId(documentId);
        return this;
    }

    public Permission build() {
        return permission;
    }

}

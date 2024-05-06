package com.example.demo.service;

import com.example.demo.model.Permission;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteResponse;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AuthorizationService {

    private OpenFgaClient fgaClient;

    public AuthorizationService(OpenFgaClient fgaClient) {
        this.fgaClient = fgaClient;
    }

    public void create(Permission permission) {
        try {
            ClientTupleKey tuple = new ClientTupleKey()
                    .user("user:" + permission.getUserId())
                    .relation(permission.getRelation())
                    ._object("document:" + permission.getDocumentId());
            ClientWriteResponse response = fgaClient.writeTuples(List.of(tuple)).get();
        } catch (FgaInvalidParameterException | InterruptedException | ExecutionException e) {
            throw new AuthorizationServiceException(e);
        }
    }
}

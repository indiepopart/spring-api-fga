package com.example.api.model;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteResponse;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AuthorizationRepository {

    private OpenFgaClient fgaClient;

    public AuthorizationRepository(OpenFgaClient fgaClient) {
        this.fgaClient = fgaClient;
    }

    public void save(Document file){
        try {
            ClientTupleKey tuple = new ClientTupleKey().user("user:" + file.getOwnerId()).relation("owner")._object("document:" + file.getId());
            ClientWriteResponse response = fgaClient.writeTuples(List.of(tuple)).get();

        } catch (FgaInvalidParameterException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}

package com.example.api.model;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationRepository {

    private OpenFgaClient fgaClient;

    public AuthorizationRepository(OpenFgaClient fgaClient) {
        this.fgaClient = fgaClient;
    }

    public void save(Document file){
        try {
            ClientTupleKey tuple = new ClientTupleKey().user("user:" + file.getOwnerId()).relation("owner")._object("document:" + file.getId());
            fgaClient.writeTuples(List.of(tuple));

        } catch (FgaInvalidParameterException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Testing transactional");

    }
}

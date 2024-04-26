package com.example.api.initializer;

import dev.openfga.autoconfigure.ConditionalOnFgaProperties;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@ConditionalOnProperty(prefix = "openfga", name = "initialize", havingValue = "true")
public class OpenFGAInitializer implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(OpenFGAInitializer.class);

    private OpenFgaClient fgaClient;

    private OpenFGAUtil openFgaUtil;


    public OpenFGAInitializer(OpenFgaClient fgaClient, OpenFGAUtil openFgaUtil) {
        this.fgaClient = fgaClient;
        this.openFgaUtil = openFgaUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        CreateStoreRequest storeRequest = new CreateStoreRequest().name("test");
        try {
            CreateStoreResponse storeResponse = fgaClient.createStore(storeRequest).get();
            logger.info("Created store: {}", storeResponse);
            fgaClient.setStoreId(storeResponse.getId());

            WriteAuthorizationModelRequest modelRequest = new WriteAuthorizationModelRequest();
            AuthorizationModel model = openFgaUtil.convertJsonToAuthorizationModel("classpath:fga/auth-model.json");
            modelRequest.setTypeDefinitions(model.getTypeDefinitions());
            modelRequest.setConditions(model.getConditions());
            modelRequest.setSchemaVersion(model.getSchemaVersion());
            WriteAuthorizationModelResponse modelResponse = fgaClient.writeAuthorizationModel(modelRequest).get();
            logger.info("Created model: {}", modelResponse);
            fgaClient.setAuthorizationModelId(modelResponse.getAuthorizationModelId());

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error writing to FGA", e);
        }
    }


}

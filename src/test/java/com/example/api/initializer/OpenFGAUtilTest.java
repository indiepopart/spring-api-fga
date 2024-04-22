package com.example.api.initializer;

import dev.openfga.sdk.api.model.AuthorizationModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class OpenFGAUtilTest {

    private Logger logger = LoggerFactory.getLogger(OpenFGAUtilTest.class);

    @Autowired
    private OpenFGAUtil openFgaUtil;

    @Test
    public void testMapper(){
        AuthorizationModel authorizationModel = openFgaUtil.convertJsonToAuthorizationModel("classpath:test-model.json");
        assertNotNull(authorizationModel);
        assertEquals(3, authorizationModel.getTypeDefinitions().size());
        assertEquals("user", authorizationModel.getTypeDefinitions().get(0).getType());
        assertEquals("document", authorizationModel.getTypeDefinitions().get(1).getType());
        assertEquals("domain", authorizationModel.getTypeDefinitions().get(2).getType());
    }
}

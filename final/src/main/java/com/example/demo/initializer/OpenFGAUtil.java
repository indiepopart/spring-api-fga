package com.example.demo.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.openfga.sdk.api.model.AuthorizationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class OpenFGAUtil {

    private static Logger logger = LoggerFactory.getLogger(OpenFGAUtil.class);

    private ResourceLoader resourceLoader;

    private ObjectMapper objectMapper;

    public OpenFGAUtil(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public AuthorizationModel convertJsonToAuthorizationModel(String path){
        try {
            Resource resource = resourceLoader.getResource(path);
            String json = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
            logger.debug(json);
            AuthorizationModel authorizationModel = objectMapper.readValue(json, AuthorizationModel.class);
            logger.debug(authorizationModel.toString());
            return authorizationModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

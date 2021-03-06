package com.github.scribejava.apis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.Response;
import java.io.IOException;

/**
 * non standard Facebook Extractor
 */
public class FacebookAccessTokenJsonExtractor extends OAuth2AccessTokenJsonExtractor {

    protected FacebookAccessTokenJsonExtractor() {
    }

    private static class InstanceHolder {

        private static final FacebookAccessTokenJsonExtractor INSTANCE = new FacebookAccessTokenJsonExtractor();
    }

    public static FacebookAccessTokenJsonExtractor instance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * non standard. examples:<br>
     *
     * '{"error":{"message":"This authorization code has been
     * used.","type":"OAuthException","code":100,"fbtrace_id":"DtxvtGRaxbB"}}'<br>
     *
     * '{"error":{"message":"Error validating application. Invalid application
     * ID.","type":"OAuthException","code":101,"fbtrace_id":"CvDR+X4WWIx"}}'
     *
     * @param response response
     */
    @Override
    public void generateError(Response response) throws IOException {
        final JsonNode errorNode = OAuth2AccessTokenJsonExtractor.OBJECT_MAPPER
                .readTree(response.getBody())
                .get("error");

        throw new FacebookAccessTokenErrorResponse(errorNode.get("message").asText(), errorNode.get("type").asText(),
                errorNode.get("code").asInt(), errorNode.get("fbtrace_id").asText(), response);
    }

}

package com.consort.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import spark.Response;

import java.io.InputStream;
import java.net.URLConnection;

public class GitlabConnectionHandler {

    private String server;
    private String token;

    public GitlabConnectionHandler(final String server, final String token) {
        this.server = server;
        this.token = token;
    }

    public String getJsonFromGitlab() throws Exception {

        Validate.notEmpty(server);
        Validate.notEmpty(token);

        HttpGet httpGet = new HttpGet(server);
        httpGet.setHeader("PRIVATE-TOKEN", token);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            final CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            final HttpEntity responseEntity = httpResponse.getEntity();
            return IOUtils.toString(responseEntity.getContent(), "UTF-8");
        }
    }

    public void getRawFileFromGItlab(Response res) throws Exception {

        Validate.notEmpty(server);
        Validate.notEmpty(token);

        HttpGet httpGet = new HttpGet(server);
        httpGet.setHeader("PRIVATE-TOKEN", token);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            final CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            final HttpEntity responseEntity = httpResponse.getEntity();
            responseEntity.writeTo(res.raw().getOutputStream());
        }
    }
}

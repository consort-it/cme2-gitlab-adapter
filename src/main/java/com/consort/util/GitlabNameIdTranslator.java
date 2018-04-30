package com.consort.util;

import com.consort.controller.GitlabConnectionHandler;
import com.consort.entities.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;

import java.util.List;

public class GitlabNameIdTranslator {

    private String server;
    private String token;

    public GitlabNameIdTranslator(final String server, final String token) {
        this.server = server;
        this.token = token;
    }

    public String translateNameToId(final String name) {
        Validate.notEmpty(server);
        Validate.notEmpty(token);
        Validate.notEmpty(name);

        setProtocol();

        return getProjectId(name);
    }

    public String getServer() {
        return server;
    }

    public void setServer(final String server) {
        this.server = server;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    void setProtocol() {
        if (!server.startsWith("https")) {
            if (server.startsWith("http")) {
                this.server = this.server.replaceAll("^http", "https");
            } else {
                this.server = "https://" + server;
            }
        }

        this.server = server + "/api/v4/projects";
    }

    String getProjectId(final String name) {

        String projectId = "";

        final GitlabConnectionHandler gitlabConnectionHandler = new GitlabConnectionHandler(server, token);
        try {
            final String projectsJSON = gitlabConnectionHandler.getJsonFromGitlab();
            final ObjectMapper mapper = new ObjectMapper();
            final List<Project> projectList = mapper.readValue(projectsJSON, new TypeReference<List<Project>>(){});
            for (final Project project : projectList) {
                if (project.getName().equalsIgnoreCase(name)) {
                    projectId = "" + project.getId();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectId;
    }
}

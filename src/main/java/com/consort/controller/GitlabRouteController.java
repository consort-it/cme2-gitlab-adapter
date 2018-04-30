package com.consort.controller;

import com.consort.entities.Commit;
import com.consort.security.AuthorizationFilter;
import com.consort.util.EnvironmentContext;
import com.consort.util.ErrorMessage;
import com.consort.util.GitlabNameIdTranslator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;
import java.net.URLConnection;
import java.util.List;

public class GitlabRouteController implements RouteController {

    private static final String BASE_URI = "/api/v1/gitlab-adapter";
    private static final String AUTHORIZER_NAME = "scope";
    private static final String ROLE_ADMIN = "aws.cognito.signin.user.admin";

    final String gitlabHosts = EnvironmentContext.getInstance().getenv("gitlab_hosts");
    final String accessTokens = EnvironmentContext.getInstance().getenv("gitlab_personal_access_token");

    final Logger logger = LoggerFactory.getLogger(GitlabRouteController.class);

    final ObjectMapper mapper = new ObjectMapper();

    public void initRoutes() {
        final Service http = Service.ignite().port(8080);

        enableCORS(http, "*", "GET, POST, OPTIONS", "Content-Type, Authorization");

        http.before("/api/v1/gitlab-adapter/*", new AuthorizationFilter(AUTHORIZER_NAME, ROLE_ADMIN));

        http.notFound((req, res) -> {
            res.type("application/json");
            return mapper.writeValueAsString(new ErrorMessage("MDS-1234", "404 URL not found"));
        });

        http.internalServerError((req, res) -> {
            res.type("application/json");
            return mapper.writeValueAsString(new ErrorMessage("MDS-1234", "500 Internal Server Error"));
        });

        http.before("/api/v1/metadata-service/*", new AuthorizationFilter(AUTHORIZER_NAME, ROLE_ADMIN));

        initContentAsStringRoute(http);
        initContentAsRawRoute(http);
        initMetadataRoute(http);
    }

    private void initContentAsStringRoute(final Service http) {

        http.get(BASE_URI + "/:microservice-name/content-as-string/:filepath", (req, res) -> {

            try {

                final String microserviceName = req.params("microservice-name");
                final String pathToFile = req.params("filepath");

                logger.info("The Route /api/v1/gitlab-adapter/" + microserviceName + "/content-as-string/" + pathToFile );

                res.status(200);
                res.header("content-Type","text/plain");

                // use server and token to get id for project name
                final GitlabNameIdTranslator idTranslator = new GitlabNameIdTranslator(gitlabHosts, accessTokens);
                final String projectId = idTranslator.translateNameToId(microserviceName);

                // use project id to configure the server api call
                final String serverConfiguration = new ServerConfigurator().getServerConfigurationForFiles(gitlabHosts, projectId, pathToFile, true);
                final GitlabConnectionHandler connectionHandler = new GitlabConnectionHandler(serverConfiguration, accessTokens);

                return connectionHandler.getJsonFromGitlab();

            } catch (Exception e) {

                res.status(400);
                return mapper.writeValueAsString(new ErrorMessage("GIT-1234", "Something was wrong with the client request"));

            }
        });
    }

    private void initContentAsRawRoute(final Service http) {

        http.get(BASE_URI + "/:microservice-name/content-as-raw/:filepath", (req, res) -> {

            try {

                res.status(200);

                final String microserviceName = req.params("microservice-name");
                final String pathToFile = req.params("filepath");

                logger.info("The Route /api/v1/gitlab-adapter/" + microserviceName + "/content-as-raw/" + pathToFile );

                String mimeType = URLConnection.guessContentTypeFromName(pathToFile);
                res.header("content-Type", mimeType);

                // use server and token to get id for project name
                final GitlabNameIdTranslator idTranslator = new GitlabNameIdTranslator(gitlabHosts, accessTokens);
                final String projectId = idTranslator.translateNameToId(microserviceName);

                // use project id to configure the server api call
                final String serverConfiguration = new ServerConfigurator().getServerConfigurationForFiles(gitlabHosts, projectId, pathToFile, true);
                final GitlabConnectionHandler connectionHandler = new GitlabConnectionHandler(serverConfiguration, accessTokens);

                connectionHandler.getRawFileFromGItlab(res);

                return "";

            } catch (Exception e) {

                res.status(400);
                return mapper.writeValueAsString(new ErrorMessage("GIT-1234", "Something was wrong with the client request"));

            }
        });
    }

    private void initMetadataRoute(final Service http) {

        http.get( BASE_URI + "/:microservice-name/metadata" , (req, res) -> {

            try {

                res.status(200);

                final String microserviceName = req.params("microservice-name");
                int limit = (req.queryParams("limit") == null) ? 5 : Integer.parseInt(req.queryParams("limit")) ;

                logger.info("The Route /api/v1/gitlab-adapter/" + microserviceName + "/metadata" );

                // use server and token to get id for project name
                final GitlabNameIdTranslator idTranslator = new GitlabNameIdTranslator(gitlabHosts, accessTokens);
                final String projectId = idTranslator.translateNameToId(microserviceName);

                // use commit id to get commit metadata
                final String serverConfigForCommitMetadata = new ServerConfigurator().getCommitMetadata(gitlabHosts, projectId, "");
                final GitlabConnectionHandler commitMetadataHandler = new GitlabConnectionHandler(serverConfigForCommitMetadata, accessTokens);

                List<Commit> commits = mapper.readValue(commitMetadataHandler.getJsonFromGitlab(), new TypeReference<List<Commit>>(){});

                if (commits.size() < limit) {
                    return mapper.writeValueAsString(commits);
                } else {
                    return mapper.writeValueAsString(commits.subList(0, limit));
                }

            } catch (Exception e) {

                res.status(400);
                return mapper.writeValueAsString(new ErrorMessage("GIT-1234", "Something was wrong with the client request"));

            }
        });

        http.get(BASE_URI + "/:microservice-name/metadata/:filepath", (req, res) -> {

            try {

                res.status(200);

                final String microserviceName = req.params("microservice-name");
                final String pathToFile = req.params("filepath");
                int limit = (req.queryParams("limit") == null) ? 5 : Integer.parseInt(req.queryParams("limit")) ;

                logger.info("The Route /api/v1/gitlab-adapter/" + microserviceName + "/metadata/" + pathToFile );

                // use server and token to get id for project name
                final GitlabNameIdTranslator idTranslator = new GitlabNameIdTranslator(gitlabHosts, accessTokens);
                final String projectId = idTranslator.translateNameToId(microserviceName);

                // use commit id to get commit metadata
                final String serverConfigForCommitMetadata = new ServerConfigurator().getCommitMetadata(gitlabHosts, projectId, pathToFile);
                final GitlabConnectionHandler commitMetadataHandler = new GitlabConnectionHandler(serverConfigForCommitMetadata, accessTokens);

                List<Commit> commits = mapper.readValue(commitMetadataHandler.getJsonFromGitlab(), new TypeReference<List<Commit>>(){});

                if (commits.size() < limit) {
                    return mapper.writeValueAsString(commits);
                } else {
                    return mapper.writeValueAsString(commits.subList(0, limit));
                }

            } catch (Exception e) {

                res.status(400);
                return mapper.writeValueAsString(new ErrorMessage("GIT-1234", "Something was wrong with the client request"));

            }
        });
    }

    private static void enableCORS(final Service http, final String origin, final String methods, final String headers) {

        http.options("/*", (req, res) -> {

            final String acRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (acRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", acRequestHeaders);
            }

            final String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        http.before((req, res) -> {
            res.header("Access-Control-Allow-Origin", origin);
            res.header("Access-Control-Request-Method", methods);
            res.header("Access-Control-Allow-Headers", headers);
            res.type("application/json");
            res.header("Server", "-");
        });

    }
}

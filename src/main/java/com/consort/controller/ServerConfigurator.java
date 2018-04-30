package com.consort.controller;

class ServerConfigurator {

    public String getServerConfigurationDefault(final String server, final String projectId) {
        String url = "";

        url = (server.startsWith("https://") ? server : "https://" + server)
                + "/api/v4/projects/"
                + projectId;

        if(!url.endsWith("?ref=master")) {
            url = url + "?ref=master";
        }

        return url;
    }

    public String getServerConfigurationForFiles(final String server, final String projectId, final String pathToFile, boolean rawFile) {
        String url = "";

        url = (server.startsWith("https://") ? server : "https://" + server)
                + "/api/v4/projects/"
                + projectId
                + "/repository/files/"
                + pathToFile.replaceAll("\\.", "%2E").replaceAll("/", "%2F")
                + (rawFile ? "/raw" : "");

        if(!url.endsWith("?ref=master")) {
            url = url + "?ref=master";
        }

        return url;
    }

    public String getCommitMetadata(final String server, final String projectId, final String pathToFile) {

        String url = "";

        url = (server.startsWith("https://") ? server : "https://" + server)
                + "/api/v4/projects/"
                + projectId
                + "/repository/commits";

        if(!url.endsWith("?ref=master")) {
            url += "?ref=master";
        }

        if (!pathToFile.isEmpty()) {
            url += "&path=" + pathToFile.replaceAll("/", "%2F");
        }

        return url;
    }
}

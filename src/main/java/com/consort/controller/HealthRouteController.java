package com.consort.controller;

import com.consort.entities.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Service;

import static spark.Service.ignite;

public class HealthRouteController implements RouteController {

    public void initRoutes() {
        final Service http = ignite().port(8081);
        http.get("/health", (req, res) -> {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(new Status("UP"));
        });
    }
}

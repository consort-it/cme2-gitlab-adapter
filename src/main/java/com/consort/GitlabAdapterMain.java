package com.consort;

import com.consort.controller.GitlabRouteController;
import com.consort.controller.HealthRouteController;
import com.consort.controller.RouteController;

import java.util.HashSet;
import java.util.Set;

public class GitlabAdapterMain {

    private static final Set<RouteController> routeControllers = new HashSet<>();

    public static void main(final String[] args) {

        registerRouteControllers();

        for (final RouteController routeController : routeControllers) {
            routeController.initRoutes();
        }
    }

    private static void registerRouteControllers() {
        routeControllers.add(new GitlabRouteController());
        routeControllers.add(new HealthRouteController());
    }
}

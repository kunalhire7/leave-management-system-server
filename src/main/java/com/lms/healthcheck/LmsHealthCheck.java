package com.lms.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class LmsHealthCheck extends HealthCheck {

    private final String version;

    public LmsHealthCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("OK with version: " + this.version);
    }
}

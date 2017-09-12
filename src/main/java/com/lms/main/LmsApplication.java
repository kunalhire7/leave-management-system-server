package com.lms.main;

import com.lms.config.LmsConfig;
import com.lms.healthcheck.LmsHealthCheck;
import com.lms.resources.LeaveResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class LmsApplication extends Application<LmsConfig> {

    public static void main(String[] args) throws Exception {
        new LmsApplication().run(args);
    }

    @Override
    public void run(LmsConfig configuration, Environment env) throws Exception {
        final LeaveResource leaveResource = new LeaveResource();
        env.jersey().register(leaveResource);

        final LmsHealthCheck healthCheck = new LmsHealthCheck(configuration.getVersion());
        env.healthChecks().register("template", healthCheck);
        env.jersey().register(healthCheck);
    }
}

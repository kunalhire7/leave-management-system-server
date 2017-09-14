package com.lms.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.config.LmsConfig;
import com.lms.healthcheck.LmsHealthCheck;
import com.lms.utils.ObjectMapperUtil;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class LmsApplication extends Application<LmsConfig> {

    private MongoBundle<LmsConfig> mongoBundle;

    public static void main(String[] args) throws Exception {
        new LmsApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<LmsConfig> bootstrap) {
        bootstrap.addBundle(mongoBundle = MongoBundle.<LmsConfig>builder()
                .withConfiguration(LmsConfig::getMongo)
                .build());
    }

    @Override
    public void run(LmsConfig configuration, Environment env) throws Exception {

        ObjectMapper mapper = env.getObjectMapper();
        ObjectMapperUtil.configure(mapper);

        JerseyEnvironment jersey = env.jersey();
        jersey.packages("com.lms");
        jersey.register(new DependencyBinder(env.getObjectMapper(), mongoBundle));

        final LmsHealthCheck healthCheck = new LmsHealthCheck(configuration.getVersion());
        env.healthChecks().register("template", healthCheck);
        env.jersey().register(healthCheck);
        configureCors(env);
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,PATCH,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}

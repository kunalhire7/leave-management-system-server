package com.lms.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class LmsConfig extends Configuration {

    @NotEmpty
    private String version;

    @JsonProperty
    private MongoConfiguration mongo;

    public MongoConfiguration getMongo() {
        return mongo;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }

}

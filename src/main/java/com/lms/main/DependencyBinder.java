package com.lms.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.config.LmsConfig;
import com.lms.constants.CollectionNames;
import com.lms.constants.NamedDependencies;
import com.lms.repositories.LeavesRepository;
import com.lms.services.LmsService;
import com.lms.utils.InstantDeserializer;
import com.lms.utils.InstantSerializer;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.mongodb.DB;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;

import java.time.Instant;

public class DependencyBinder extends AbstractBinder {

    private final ObjectMapper objectMapper;
    private MongoBundle<LmsConfig> mongoBundle;

    public DependencyBinder(ObjectMapper objectMapper, MongoBundle<LmsConfig> mongoBundle) {
        this.objectMapper = objectMapper;
        this.mongoBundle = mongoBundle;
    }

    @Override
    protected void configure() {
        try {
            bindDb();
            bindServices();
            bindObjectMapper();
            bindRepositories();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void bindRepositories() {
        bindAsContract(LeavesRepository.class);
    }

    private void bindDb() {
        Mapper mapper = new JacksonMapper.Builder()
                .addSerializer(Instant.class, new InstantSerializer())
                .addDeserializer(Instant.class, new InstantDeserializer())
                .build();
        DB db = mongoBundle.getDB();
        Jongo jongo = new Jongo(db, mapper);
        bind(jongo.getCollection(CollectionNames.LEAVES)).named(NamedDependencies.LEAVES_COLLECTION).to(MongoCollection.class);
    }

    private void bindObjectMapper() {
        bind(objectMapper).to(ObjectMapper.class);
    }

    private void bindServices() {
        bindAsContract(LmsService.class);
    }
}

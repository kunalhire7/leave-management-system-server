package com.lms.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.TimeZone;

/**
 * Utility methods for ObjectMapper
 */
public final class ObjectMapperUtil {

    private ObjectMapperUtil() { }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        configure(mapper);
        return mapper;
    }

    public static void configure(ObjectMapper mapper) {
        mapper.enable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.findAndRegisterModules();
        mapper.registerModule(new PriorityJacksonModule());
    }
}

class PriorityJacksonModule extends SimpleModule {

    PriorityJacksonModule() {
        super("PriorityJacksonModule", new Version(1, 0, 0, null, null, null));
        addSerializer(ObjectId.class, new ToStringSerializer());
        addDeserializer(ObjectId.class, new PriorityObjectIdDeserializer());
    }
}

/**
 * Deserializes the textual representation of an ObjectId into an ObjectId
 */
class PriorityObjectIdDeserializer extends StdDeserializer<ObjectId> {

    PriorityObjectIdDeserializer() {
        super(ObjectId.class);
    }

    @Override
    public ObjectId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        return new ObjectId(parser.getText());
    }
}
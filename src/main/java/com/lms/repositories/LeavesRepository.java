package com.lms.repositories;

import com.lms.domain.Leave;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Named;

import static com.lms.constants.NamedDependencies.LEAVES_COLLECTION;

@Service
public class LeavesRepository {

    private MongoCollection collection;

    @Inject
    public LeavesRepository(@Named(LEAVES_COLLECTION) MongoCollection collection) {
        this.collection = collection;
    }

    public Leave save(Leave leaveToBeSaved) {
        WriteResult writeResult = collection.save(leaveToBeSaved);
        return writeResult.getN() > 0 ? leaveToBeSaved : null;
    }

    public Leave getById(String id) {
        return collection.findOne(new ObjectId(id)).as(Leave.class);
    }
}


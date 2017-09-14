package com.lms.repositories;

import com.google.common.collect.Lists;
import com.lms.domain.Leave;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

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

    public List<Leave> getAll() {
        MongoCursor<Leave> cursor = collection.find().as(Leave.class);
        return Lists.newArrayList(cursor.iterator());
    }
}


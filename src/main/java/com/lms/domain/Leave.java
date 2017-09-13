package com.lms.domain;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.time.Instant;

public class Leave {
    @MongoId
    private ObjectId _id;
    private String reason;
    private Instant fromDate;
    private Instant toDate;
    private LeaveType type;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }
}

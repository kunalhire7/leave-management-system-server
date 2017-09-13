package com.lms.services

import com.lms.domain.Leave
import com.lms.domain.LeaveType
import com.lms.repositories.LeavesRepository
import com.lms.utils.InstantDeserializer
import com.lms.utils.InstantSerializer
import com.mongodb.DB
import com.mongodb.MongoClient
import org.jongo.Jongo
import org.jongo.Mapper
import org.jongo.MongoCollection
import org.jongo.marshall.jackson.JacksonMapper
import spock.lang.Specification

import java.time.Instant

class LmsServiceSpec extends Specification {

    MongoCollection leavesCollection
    LeavesRepository leavesRepository
    LmsService lmsService

    def setup() {
        DB db = new MongoClient("localhost", 27017).getDB("lms_test")
        Mapper mapper = new JacksonMapper.Builder()
                .addSerializer(Instant.class, new InstantSerializer())
                .addDeserializer(Instant.class, new InstantDeserializer())
                .build()
        Jongo jongo = new Jongo(db, mapper)
        leavesCollection = jongo.getCollection("leaves")
        leavesCollection.drop()
        leavesRepository = new LeavesRepository(leavesCollection)
        lmsService = new LmsService(leavesRepository)
    }

    def "save() should save the leave into database "() {
        given:
        def leave = new Leave(fromDate: Instant.now(), toDate: Instant.now().plusSeconds(5),
                type: LeaveType.PAID_LEAVES, reason: "vacations")

        when:
        lmsService.save(leave)

        then:
        def savedLeave = leavesCollection.findOne().as(Leave)
        savedLeave.fromDate == leave.fromDate
    }

    def "getById() should return the leave based on Id"() {
        given:
        def savedLeave = lmsService.save(new Leave(fromDate: Instant.now(), toDate: Instant.now().plusSeconds(5),
                type: LeaveType.PAID_LEAVES, reason: "vacations"))

        when:
        def leave = lmsService.getById(savedLeave.getId().toString())

        then:
        leave.fromDate == savedLeave.fromDate
    }

}

package com.firstP.jour.repository;

import com.firstP.jour.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}

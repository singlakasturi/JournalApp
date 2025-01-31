package com.firstP.jour.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntry {
    @Id
    @NonNull
    private ObjectId id;
    private String title;
    private String description;
    private LocalDateTime date;

    private List<JournalEntry> journalEntries = new ArrayList<>();
}

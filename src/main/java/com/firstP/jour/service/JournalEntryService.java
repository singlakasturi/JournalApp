package com.firstP.jour.service;

import com.firstP.jour.entity.JournalEntry;
import com.firstP.jour.entity.users;
import com.firstP.jour.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private userService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
            users user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteById(ObjectId MyId, String userName) {
        boolean removed = false;
        try {
            users user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(MyId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(MyId);
            }
        }
        catch (Exception e) {
            System.out.println("Error deleting journal entry: " + e.getMessage());
        }
        return removed;
    }

    public void saveEntry(JournalEntry MyEntry) {
        journalEntryRepository.save(MyEntry);
    }
}
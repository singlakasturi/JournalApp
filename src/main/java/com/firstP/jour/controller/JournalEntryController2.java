package com.firstP.jour.controller;

import com.firstP.jour.entity.JournalEntry;
import com.firstP.jour.entity.users;
import com.firstP.jour.service.JournalEntryService;
import com.firstP.jour.service.userService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    userService userService;

//    @GetMapping("/{userName}")
//    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable String userName) {
//        users user = userService.findByUserName(userName);
//        List<JournalEntry> all = user.getJournalEntries();
//        if(all != null && !all.isEmpty())
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @PostMapping("/{userName}")
//    public ResponseEntity<JournalEntry> addJournalEntry(@PathVariable String userName, @RequestBody JournalEntry MyEntry) {
//
//        try{
//            journalEntryService.saveEntry(MyEntry, userName);
//            return new ResponseEntity<>(MyEntry, HttpStatus.CREATED);
//        }catch(Exception e){
//            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("id/{myId}")
//    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
//        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
//        if(journalEntry.isPresent()) {
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @DeleteMapping("id/{userName}/{myId}")
//    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName, @PathVariable ObjectId myId) {
//        journalEntryService.deleteById(myId, userName);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @PutMapping("id/{userName}/{myId}")
//    public ResponseEntity<?> updateJournalById(
//            @PathVariable ObjectId myId,
//            @RequestBody JournalEntry newEntry,
//            @PathVariable String userName)
//
//    {
//        JournalEntry old = journalEntryService.findById(myId).orElse((null));
//        if(old != null) {
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
//            old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



    //                          SECURE ENDPOINTS                          //

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        users user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry MyEntry) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(MyEntry, userName);
            return new ResponseEntity<>(MyEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("Authenticated username: " + userName);

        users user = userService.findByUserName(userName);
        if (user == null) {
            System.out.println("User not found for username: " + userName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Debug journal entries
        System.out.println("Journal entries for user: " + user.getJournalEntries());

        // Check if the user owns a journal entry with the given ID
        boolean ownsEntry = user.getJournalEntries().stream()
                .anyMatch(entry -> entry.getId().equals(myId));
        if (!ownsEntry) {
            System.out.println("User does not own the journal entry with ID: " + myId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Fetch journal entry from the service
        System.out.println("Finding journal entry by ID: " + myId);
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isEmpty()) {
            System.out.println("Journal entry not found for ID: " + myId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Success response
        System.out.println("Journal entry found: " + journalEntry.get());
        return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        users user = userService.findByUserName(userName);
        if (user == null) {
            System.out.println("User not found for username: " + userName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean ownsEntry = user.getJournalEntries().stream()
                .anyMatch(entry -> entry.getId().equals(myId));
        if (!ownsEntry) {
            System.out.println("User does not own the journal entry with ID: " + myId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            JournalEntry old = journalEntry.get();
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : old.getDescription());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

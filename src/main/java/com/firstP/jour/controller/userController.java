package com.firstP.jour.controller;

import com.firstP.jour.entity.JournalEntry;
import com.firstP.jour.entity.users;
import com.firstP.jour.repository.userRepository;
import com.firstP.jour.service.JournalEntryService;
import com.firstP.jour.service.userService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;
    @Autowired
    private userRepository userRepository;



    @GetMapping
    public ResponseEntity<List<users>> getAllUsers() {
        List<users> all = userService.getAll();
        if(all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping("/id/{id}")
    public ResponseEntity<users> findUser(@PathVariable ObjectId id) {
        Optional<users> user = userService.findById(id);
        if(user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
//        userService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @PostMapping("/id/{id}")
//    public ResponseEntity<users> updateUserById(@PathVariable ObjectId id, @RequestBody users user) {
//        users old = userService.findById(id).orElse(null);
//        if(old != null){
//            old.setPassWord(!user.getPassWord().isEmpty() ? user.getPassWord() : old.getPassWord());
//            userService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


    //                          SECURE ENDPOINTS                          //

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody users user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            users userById = userService.findByUserName(username);
            if (userById == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            if (!user.getUserName().isEmpty()) {
                userById.setUserName(user.getUserName());
            }
            if (!user.getPassWord().isEmpty()) {
                userById.setPassWord(user.getPassWord());
            }

            userService.saveNewUser(userById);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
         userRepository.deleteByUserName(username);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping
//    public ResponseEntity<users> findUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//    }
}

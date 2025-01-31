package com.firstP.jour.service;

import com.firstP.jour.entity.JournalEntry;
import com.firstP.jour.entity.users;
import com.firstP.jour.repository.JournalEntryRepository;
import com.firstP.jour.repository.userRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class userService {
    @Autowired
    private userRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(userService.class);


    public void saveNewUser(users user) {
        try{
            if(findByUserName(user.getUserName()) != null)
                throw new Exception("User already exists");
            String encodedPassword = passwordEncoder.encode(user.getPassWord());
            user.setPassWord(encodedPassword);
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }
        catch (Exception e) {
            log.info("hehehehehe");
            log.error("hehehehehe" + e);
            log.warn("hehehehehe");
            log.debug("hehehehehe");
            log.trace("hehehehehe");
        }
    }

    public void saveUser(users user) {
        userRepository.save(user);
    }

    public List<users> getAll() {
        return userRepository.findAll();
    }

    public Optional<users> findById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteById(ObjectId MyId) {
        userRepository.deleteById(MyId);
    }

    public users findByUserName (String userName) {
        return userRepository.findByUserName(userName);
    }

    public void saveAdmin(users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(encodedPassword);
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }
}

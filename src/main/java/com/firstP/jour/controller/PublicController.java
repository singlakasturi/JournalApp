package com.firstP.jour.controller;


import com.firstP.jour.entity.users;
import com.firstP.jour.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private userService userService;

    @GetMapping("/health")
    public String HealthCheck() {
        return "Health OK";
    }



    @PostMapping("/createUser")
    public ResponseEntity<users> createUser(@RequestBody users user) {
        if (user.getPassWord().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
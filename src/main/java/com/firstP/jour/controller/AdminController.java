package com.firstP.jour.controller;


import com.firstP.jour.entity.users;
import com.firstP.jour.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private userService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<users> all = userService.getAll();

        if(!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("create-user-admin")
    public ResponseEntity<?> createUserAdmin(@RequestBody users user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

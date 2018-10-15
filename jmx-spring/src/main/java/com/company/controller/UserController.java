package com.company.controller;

import com.company.entity.User;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedResource
@RestController
@RequestMapping(value = "/users")
public class UserController {
    static List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    private boolean isDatabase = true;

    @ManagedAttribute
    public boolean isDatabaseSave() {
        return isDatabase;
    }

    @ManagedAttribute
    public void setDatabaseSave(boolean isDatabase) {
        this.isDatabase = isDatabase;
    }



    @PostMapping("/save/{username}")

    public User save(@PathVariable("username") String username) {
        User user = User.builder()
                .username(username)
                .build();

        if (isDatabase) {
            userRepository.save(user);
        } else {
            user.setId(UUID.randomUUID().toString());
            users.add(user);
        }

        return user;

    }

    @GetMapping("/list-users")
    public List<User> getListUsers() {
        return users;
    }

    @GetMapping("/mongo-users")
    public List<User> getMongoUsers() {
        return userRepository.findAll();
    }
}

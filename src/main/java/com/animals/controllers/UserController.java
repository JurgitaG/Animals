package com.animals.controllers;

import com.animals.models.Users;
import com.animals.requests.UsersRequest;
import com.animals.services.UserService;
import com.animals.repositories.UsersRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private final UsersRepository repository;

    private final UserService userService;

    public UserController(
            final UsersRepository repository,
            final UserService userService
    ) {
        this.repository = repository;
        this.userService = userService;
    }

    @PostMapping
    public Users createUser(@RequestBody UsersRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

}
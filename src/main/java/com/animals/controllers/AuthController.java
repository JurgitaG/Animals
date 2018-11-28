package com.animals.controllers;

import com.animals.models.Users;
import com.animals.requests.LoginUserRequest;
import com.animals.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "user/login")
    public Users login(@RequestBody  LoginUserRequest request) {
        return userService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping(path = "user/logout", headers = {"Authorization"})
    public ResponseEntity logout(@AuthenticationPrincipal Users user) {
        userService.logout(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
package com.mgiglione.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mgiglione.model.User;
import com.mgiglione.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;   

    @GetMapping(value = "/async/{name}")
    @Async
    public CompletableFuture<User[]> searchASync(@PathVariable(name = "name") String title) {
        return CompletableFuture.completedFuture(userService.getUserByName(title));
    }

    @GetMapping(value = "/sync/{name}")
    public @ResponseBody User[] searchSync(@PathVariable(name = "name") String title) {
        return userService.getUserByName(title);
    }

}

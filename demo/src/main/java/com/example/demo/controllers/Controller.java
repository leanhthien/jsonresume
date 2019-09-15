package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
@RequestMapping(path="/demo")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

}
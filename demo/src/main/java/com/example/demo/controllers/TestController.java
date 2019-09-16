package com.example.demo.controllers;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
@RequestMapping(path="/demo")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @GetMapping(path="/allUser")
    public @ResponseBody Iterable<AppUser> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping(path="/allRole")
    public @ResponseBody Iterable<UserRole> getAllRoles() {

        return roleRepository.findAll();
    }

}
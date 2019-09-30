package com.example.demo.services;

import com.example.demo.entity.AppUser;

public interface UserService {

    AppUser getUserByName(String name);

    AppUser saveOrUpdateUser(AppUser user);

}

package com.example.demo.services;

import com.example.demo.domain.User;

public interface UserService {

    User getUserByName(String name);

    User saveOrUpdateUser(User user);

}

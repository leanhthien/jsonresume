package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;

public interface UserService {

    AppUser getUserByName(String name);

    AppUser saveOrUpdateUser(AppUser user);

    String validateToken(String token, Long userId);

    Token setToken(AppUser user);

    String deleteToken(String token);

    String deleteUser(long id);

}

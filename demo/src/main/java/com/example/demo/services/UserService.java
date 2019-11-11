package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.model.Response;

public interface UserService {

    AppUser getUserByName(String name);

    AppUser saveOrUpdateUser(AppUser user);

    Response<String> validateToken(String token);

    Token setToken(AppUser user);

    String deleteToken(String token);

    String deleteUser(long id);

}

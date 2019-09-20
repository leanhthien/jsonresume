package com.example.demo.jdbc;

import com.example.demo.entity.AppUser;

public interface JdbcUserRepository {

  AppUser getUserByName(String name);

  AppUser saveOrUpdateUser(AppUser user);
}

package com.example.demo.rowMapper;

import com.example.demo.entity.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AppUserRowMapper implements RowMapper<AppUser> {

  @Override
  public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
    AppUser appUser = new AppUser();
    appUser.setUserId(resultSet.getLong("user_id"));
    appUser.setUserName(resultSet.getString("user_name"));
    appUser.setEncryptedPassword(resultSet.getString("encrypted_password"));
    appUser.setEnabled(resultSet.getBoolean("enabled"));
    return appUser;
  }
}

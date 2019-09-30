package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("jdbc")
public class UserServiceJdbcImpl implements UserService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/myDb";
  private static final String USER = "root";
  private static final String PASS = "0987654321";
  private static final int INVALID_ID = -1;

  @Override
  public AppUser getUserByName(String userName) {

    Connection conn = null;
    PreparedStatement preparedStatement;
    AppUser appUser = new AppUser();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM APP_USER WHERE USER_ID = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, userName);

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        long user_id = rs.getLong("user_id");
        String user_name = rs.getString("user_name");
        String encrypted_password = rs.getString("encrypted_password");
        boolean enabled = rs.getBoolean("enabled");

          appUser.setUserId(user_id);
          appUser.setUserName(user_name);
          appUser.setEncryptedPassword(encrypted_password);
          appUser.setEnabled(enabled);
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      appUser = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        appUser = null;
      }
    }

    return appUser;
  }

  @Override
  public AppUser saveOrUpdateUser(AppUser user) {
    return null;
  }
}

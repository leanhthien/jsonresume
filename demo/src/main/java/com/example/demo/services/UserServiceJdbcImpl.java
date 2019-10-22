package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.utils.DateUtils;
import com.example.demo.utils.RandomString;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.demo.utils.ConstUtils.*;

@Service
@Profile("api")
public class UserServiceJdbcImpl implements UserService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/myDb";
  private static final String USER = "root";
  private static final String PASS = "0987654321";

  @Override
  public AppUser getUserByName(String userName) {

    Connection conn = null;
    PreparedStatement preparedStatement;
    AppUser appUser = null;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM APP_USER WHERE USER_NAME = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, userName);

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){
        long user_id = rs.getLong("user_id");
        String user_name = rs.getString("user_name");
        String encrypted_password = rs.getString("encrypted_password");
        boolean enabled = rs.getBoolean("enabled");
          appUser = new AppUser(user_id, userName, encrypted_password, enabled);
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }

    return appUser;
  }

  @Override
  public AppUser saveOrUpdateUser(AppUser user) {
    Connection conn = null;
    PreparedStatement preparedStatement;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT COUNT(USER_ID) FROM APP_USER WHERE USER_NAME = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, user.getUserName());

      ResultSet rs = preparedStatement.executeQuery();

      int amountExistUser = INVALID_INFO;
      while(rs.next()){
        amountExistUser = rs.getInt(1);
      }

      if (amountExistUser != INVALID_INFO) {
        sql =   "INSERT INTO APP_USER (USER_NAME, ENCRYPTED_PASSWORD, ENABLED) " +
                "VALUES (?, ?, ?)";
        preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getEncryptedPassword());
        preparedStatement.setBoolean(3, true);

        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            user.setUserId(generatedKeys.getLong(1));
          }
          else {
            throw new SQLException("Creating user failed, no ID obtained.");
          }
        }

        if (user.getUserId() != null) {
          sql =   "INSERT INTO USER_ROLE (USER_ID, USER_NAME, ROLE_ID) " +
                  "VALUES (?, ?, ?)";
          preparedStatement = conn.prepareStatement(sql);

          preparedStatement.setLong(1, user.getUserId());
          preparedStatement.setString(2, user.getUserName());
          preparedStatement.setLong(3, 2L);

          preparedStatement.executeUpdate();
        }
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      user = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        user = null;
      }
    }

    return user;
  }

  @Override
  public String validateToken(String token, Long userId) {
    Connection conn = null;
    PreparedStatement preparedStatement;
    String status = FAIL;
    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT CREATED_AT, TOKEN FROM TOKEN WHERE TOKEN = ? AND USER_ID = ?";

      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, token);
      preparedStatement.setLong(2, userId);

      ResultSet rs = preparedStatement.executeQuery();

      boolean validToken = false;
      while(rs.next()){
        LocalDateTime created = rs.getTimestamp("created_at").toLocalDateTime();
        String existToken = rs.getString("token");
        if (DateUtils.isValid(created)) {
          validToken = true;
        }
        else {
          deleteToken(existToken);
        }
      }

      if (validToken) {
        status = SUCCESS;
      }

      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    return status;
  }

  @Override
  public Token setToken(AppUser user) {

    Connection conn = null;
    PreparedStatement preparedStatement;
    Token token = null;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql =   "SELECT COUNT(USER_ID) FROM TOKEN " +
                      "WHERE USER_ID = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, user.getUserId());

      ResultSet rs = preparedStatement.executeQuery();

      int amountExistTokenOfUser = INVALID_INFO;
      while(rs.next()){
        amountExistTokenOfUser = rs.getInt(1);
      }

      if (amountExistTokenOfUser < 2) {

        String random = new RandomString(32, ThreadLocalRandom.current()).nextString();

        sql =   "INSERT INTO TOKEN (TOKEN, USER_ID) " +
                "VALUES (?, ?)";
        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, random);
        preparedStatement.setLong(2, user.getUserId());

        int row = preparedStatement.executeUpdate();

        if (row != 0)
          token = new Token(random, user);

      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }

    return token;
  }

  @Override
  public String deleteToken(String token) {
    Connection conn = null;
    PreparedStatement preparedStatement;
    String status = FAIL;
    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "DELETE FROM TOKEN WHERE TOKEN = ?";

      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, token);

      int effectedUserRow = preparedStatement.executeUpdate();

      if(effectedUserRow != 0) {
        status = SUCCESS;
      }

      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    return status;
  }

  @Override
  public String deleteUser(long id) {
    Connection conn = null;
    PreparedStatement preparedStatement;
    String status = FAIL;
    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "DELETE FROM APP_USER WHERE USER_ID = ?";

      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, id);

      int effectedUserRow = preparedStatement.executeUpdate();

      if(effectedUserRow != 0) {

        sql = "DELETE FROM USER_ROLE WHERE USER_ID = ?";

        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, id);

        int effectedUserRoleRow = preparedStatement.executeUpdate();

        if (effectedUserRoleRow != 0)
          status = SUCCESS;
      }

      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    return status;
  }

}

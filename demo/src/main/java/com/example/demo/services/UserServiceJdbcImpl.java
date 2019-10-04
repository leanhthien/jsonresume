package com.example.demo.services;

import com.example.demo.entity.AppUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;


import static com.example.demo.utils.Const.INVALID_INFO;

@Service
@Profile("jdbc")
public class UserServiceJdbcImpl implements UserService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/myDb";
  private static final String USER = "root";
  private static final String PASS = "0987654321";

  @Override
  public AppUser getUserByName(String userName) {

    Connection conn = null;
    PreparedStatement preparedStatement;
    AppUser appUser = new AppUser();

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
  public String deleteUser(long id) {
    Connection conn = null;
    PreparedStatement preparedStatement;
    String status = "Fail";
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
          status = "Success";
      }

      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      status = "Fail";
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        status = "Fail";
      }
    }
    return status;
  }
}

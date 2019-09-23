package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("jdbc")
public class ProductServiceJdbcImpl implements ProductService {

  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/myDb";
  private static final String USER = "root";
  private static final String PASS = "0987654321";

  @Override
  public List<Product> listAllProducts() {

    Connection conn = null;
    Statement stmt;
    List<Product> products = new ArrayList<>();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      stmt = conn.createStatement();

      String sql =  "SELECT PRODUCT_ID, APP_USER.USER_NAME, NAME, JOB_TITLE, PRODUCT.ENABLED " +
                    "FROM PRODUCT, APP_USER " +
                    "WHERE PRODUCT.USER_ID = APP_USER.USER_ID";
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){

        long product_id = rs.getLong("product_id");
        String user_name = rs.getString("user_name");
        String name = rs.getString("name");
        String job_title = rs.getString("job_title");
        boolean enabled = rs.getBoolean("enabled");

        AppUser appUser = new AppUser(user_name);

        products.add( new Product(product_id, name, job_title, enabled, appUser));
      }
      rs.close();
      stmt.close();

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

    return products;
  }

  @Override
  public List<Product> listAllProductsByUser(String username){

    Connection conn = null;
    PreparedStatement preparedStatement;
    List<Product> products = new ArrayList<>();
    AppUser user = new AppUser();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql =  "SELECT PRODUCT_ID, USER_NAME, NAME, JOB_TITLE, PRODUCT.ENABLED " +
                    "FROM PRODUCT, APP_USER " +
                    "WHERE PRODUCT.USER_ID = APP_USER.USER_ID AND APP_USER.USER_NAME = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, username);

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){

        long product_id = rs.getLong("product_id");
        String user_name = rs.getString("user_name");
        String name = rs.getString("name");
        String job_title = rs.getString("job_title");
        boolean enabled = rs.getBoolean("enabled");

        user.setUserName(user_name);

        products.add( new Product(product_id, name, job_title, enabled, user));
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

    return products;
  }

  @Override
  public Product getProductById(Long id){
    Connection conn = null;
    PreparedStatement preparedStatement;
    Product product = new Product();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      while(rs.next()){

        long product_id = rs.getLong("product_id");
        String name = rs.getString("name");
        String job_title = rs.getString("job_title");
        String address = rs.getString("address");
        String telephone = rs.getString("telephone");
        String email = rs.getString("email");
        String website = rs.getString("website");
        String language = rs.getString("language");
        String about = rs.getString("about");
        boolean enabled = rs.getBoolean("enabled");

        product = new Product(product_id, name, job_title, address, telephone, email, website, language, about, enabled);

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

    return product;
  }

  @Override
  public Product saveOrUpdateProduct(Product product, String username){
    Connection conn = null;
    PreparedStatement preparedStatement;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String findUserSQL =  "SELECT USER_ID " +
                            "FROM APP_USER " +
                            "WHERE USER_NAME = ?";
      preparedStatement = conn.prepareStatement(findUserSQL);

      preparedStatement.setString(1, username);

      ResultSet rs = preparedStatement.executeQuery();

      long user_id = -1;
      while(rs.next()){
        user_id = rs.getLong("user_id");
      }

      String countProductSQL =  "SELECT COUNT(PRODUCT.PRODUCT_ID) " +
                                "FROM PRODUCT, APP_USER " +
                                "WHERE PRODUCT.USER_ID = APP_USER.USER_ID AND APP_USER.USER_NAME = ?";
      preparedStatement = conn.prepareStatement(countProductSQL);

      preparedStatement.setString(1, username);

      rs = preparedStatement.executeQuery();

      int count  = 0;
      while(rs.next()){
        count = rs.getInt(1);
      }

      if (product.getProductId() == null) {
        String saveSQL =  "INSERT INTO PRODUCT (NAME, JOB_TITLE, ADDRESS, TELEPHONE, EMAIL, WEBSITE, LANGUAGE, ABOUT, ENABLED, USER_ID) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = conn.prepareStatement(saveSQL, Statement.RETURN_GENERATED_KEYS);
      }
      else {
        String updateSQL =  "UPDATE PRODUCT SET " +
                            "NAME = ?, JOB_TITLE = ?, ADDRESS = ?, TELEPHONE = ?, EMAIL = ?, WEBSITE = ?, LANGUAGE = ?, ABOUT = ? " +
                            "WHERE PRODUCT_ID = ?";
        preparedStatement = conn.prepareStatement(updateSQL);
      }

      preparedStatement.setString(1, product.getName());
      preparedStatement.setString(2, product.getJobTitle());
      preparedStatement.setString(3, product.getAddress());
      preparedStatement.setString(4, product.getTelephone());
      preparedStatement.setString(5, product.getEmail());
      preparedStatement.setString(6, product.getWebsite());
      preparedStatement.setString(7, product.getLanguage());
      preparedStatement.setString(8, product.getAbout());

      if (product.getProductId() == null) {
        preparedStatement.setBoolean(9, count == 0);
        preparedStatement.setLong(10, user_id);
      }
      else {
        preparedStatement.setLong(9, product.getProductId());
      }

      preparedStatement.executeUpdate();

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          product.setProductId(generatedKeys.getLong(1));
        }
        else {
          throw new SQLException("Creating product failed, no ID obtained.");
        }
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

    return product;
  }

  @Override
  public Product setEnabledProduct(Long productId, String username){
    Connection conn = null;
    PreparedStatement preparedStatement;
    Product product = new Product();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql ="SELECT USER_ID " +
                  "FROM APP_USER " +
                  "WHERE USER_NAME = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, username);

      ResultSet rs = preparedStatement.executeQuery();

      long user_id = -1;
      while(rs.next()){
        user_id = rs.getLong("user_id");
      }

      sql = "UPDATE PRODUCT " +
            "SET ENABLED = ? " +
            "WHERE USER_ID = ? AND ENABLED = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setBoolean(1, false);
      preparedStatement.setLong(2, user_id);
      preparedStatement.setBoolean(3, true);

      preparedStatement.executeUpdate();

      sql = "UPDATE PRODUCT " +
            "SET ENABLED = ? " +
            "WHERE PRODUCT_ID = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setBoolean(1, true);
      preparedStatement.setLong(2, productId);

      preparedStatement.executeUpdate();

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

    return product;
  }

  @Override
  public void deleteProduct(Long id){
    Connection conn = null;
    PreparedStatement preparedStatement;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
//      System.out.println("Query [DELETE FROM PRODUCT WHERE PRODUCT_ID = "+ id + "]");
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, id);

      preparedStatement.executeUpdate();

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
  }

}

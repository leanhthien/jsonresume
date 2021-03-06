package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.model.Response;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.ConstUtils.FAIL;
import static com.example.demo.utils.ConstUtils.SUCCESS;

@Service
@Profile("api")
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
      products = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        products = null;
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
      products = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        products = null;
      }
    }

    return products;
  }

  @Override
  public Product getProductById(Long id){
    Connection conn = null;
    PreparedStatement preparedStatement;
    Product product  = null;

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";

      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (!isResultSetEmpty(rs) && rs.next()) {

        long product_id = rs.getLong("product_id");
        String name = rs.getString("name");
        String job_title = rs.getString("job_title");
        String address = rs.getString("address");
        String telephone = rs.getString("telephone");
        String email = rs.getString("email");
        String website = rs.getString("website");
        String language = rs.getString("language");
        String about = rs.getString("about");
        String work_experience = rs.getString("work_experience");
        boolean enabled = rs.getBoolean("enabled");
        long user_id = rs.getLong("user_id");

        product = new Product(product_id, name, job_title, address, telephone, email, website, language, about, work_experience, enabled);

        sql =   "SELECT USER_NAME " +
                "FROM APP_USER " +
                "WHERE USER_ID = ?";
        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, user_id);

        rs = preparedStatement.executeQuery();

        if (!isResultSetEmpty(rs) && rs.next()) {
            product.setAppUser(new AppUser(user_id, rs.getString("user_name")));
        }
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      product = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        product = null;
      }
    }

    return product;
  }

  @Override
  public Product getTopProduct(String username) {
    Connection conn = null;
    PreparedStatement preparedStatement;
    Product product  = null;
    AppUser appUser;
    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql =  "SELECT USER_ID " +
                    "FROM APP_USER " +
                    "WHERE USER_NAME = ?";
      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setString(1, username);

      ResultSet rs = preparedStatement.executeQuery();

      if (!isResultSetEmpty(rs) && rs.next()) {

        long user_id = rs.getLong("user_id");
        appUser = new AppUser(user_id, username);

        sql = "SELECT * FROM PRODUCT WHERE USER_ID = ? AND ENABLED = ? ";

        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, user_id);
        preparedStatement.setBoolean(2, true);

        rs = preparedStatement.executeQuery();

        if (!isResultSetEmpty(rs) && rs.next()) {

          long product_id = rs.getLong("product_id");
          String name = rs.getString("name");
          String job_title = rs.getString("job_title");
          String address = rs.getString("address");
          String telephone = rs.getString("telephone");
          String email = rs.getString("email");
          String website = rs.getString("website");
          String language = rs.getString("language");
          String about = rs.getString("about");
          String work_experience = rs.getString("work_experience");
          boolean enabled = rs.getBoolean("enabled");

          product = new Product(product_id, name, job_title, address, telephone, email, website, language, about, work_experience, enabled);
          product.setAppUser(appUser);
        }
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      product = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        product = null;
      }
    }

    return product;
  }

  @Override
  public Response<Product> saveOrUpdateProduct(Product product, String username){
    Connection conn = null;
    PreparedStatement preparedStatement;
    String message = "";

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

      int amountAllUserProduct  = 0;
      int amountProductById = 0;

      while(rs.next()){
        amountAllUserProduct = rs.getInt(1);
      }

      String saveSQL =  "INSERT INTO PRODUCT (NAME, JOB_TITLE, ADDRESS, TELEPHONE, EMAIL, WEBSITE, LANGUAGE, ABOUT, WORK_EXPERIENCE, ENABLED, USER_ID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      String saveWithIdSQL =  "INSERT INTO PRODUCT (NAME, JOB_TITLE, ADDRESS, TELEPHONE, EMAIL, WEBSITE, LANGUAGE, ABOUT, WORK_EXPERIENCE, ENABLED, USER_ID, PRODUCT_ID) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      String updateSQL =  "UPDATE PRODUCT SET " +
                          "NAME = ?, JOB_TITLE = ?, ADDRESS = ?, TELEPHONE = ?, EMAIL = ?, WEBSITE = ?, LANGUAGE = ?, ABOUT = ?, WORK_EXPERIENCE = ? " +
                          "WHERE PRODUCT_ID = ?";

      if (product.getProductId() == null) {
        preparedStatement = conn.prepareStatement(saveSQL, Statement.RETURN_GENERATED_KEYS);
      }
      else {
        String getProductSQL =  "SELECT COUNT(PRODUCT.PRODUCT_ID) " +
                                "FROM PRODUCT " +
                                "WHERE PRODUCT_ID = ?";

        preparedStatement = conn.prepareStatement(getProductSQL);

        preparedStatement.setLong(1, product.getProductId());

        rs = preparedStatement.executeQuery();

        while(rs.next()){
          amountProductById = rs.getInt(1);
        }

        preparedStatement = (amountProductById == 0) ? conn.prepareStatement(saveWithIdSQL) : conn.prepareStatement(updateSQL);

      }

      preparedStatement.setString(1, product.getName());
      preparedStatement.setString(2, product.getJobTitle());
      preparedStatement.setString(3, product.getAddress());
      preparedStatement.setString(4, product.getTelephone());
      preparedStatement.setString(5, product.getEmail());
      preparedStatement.setString(6, product.getWebsite());
      preparedStatement.setString(7, product.getLanguage());
      preparedStatement.setString(8, product.getAbout());
      preparedStatement.setString(9, product.getWorkExperience());

      int effectedRow;
      // Create new product
      if (product.getProductId() == null) {
        preparedStatement.setBoolean(10, amountAllUserProduct == 0);
        preparedStatement.setLong(11, user_id);

        effectedRow = preparedStatement.executeUpdate();

        if (effectedRow != 1) {
          message = " Cannot excute create new product!";
        }

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            product.setProductId(generatedKeys.getLong(1));
          }
          else {
            throw new SQLException("Creating product failed, no ID obtained.");
          }
        }
      }
      // Create product with id
      else if (amountProductById == 0) {
        preparedStatement.setBoolean(10, product.isEnabled());
        preparedStatement.setLong(11, product.getAppUser().getUserId());
        preparedStatement.setLong(12, product.getProductId());
        effectedRow = preparedStatement.executeUpdate();

        if (effectedRow != 1) {
          message = " Cannot excute create new product with id!";
        }
      }
      // Update product
      else {
        preparedStatement.setLong(10, product.getProductId());
        effectedRow = preparedStatement.executeUpdate();

        if (effectedRow != 1) {
          message = " Cannot excute update product!";
        }
      }

      rs.close();
      preparedStatement.close();

    } catch(Exception se){
      se.printStackTrace();
      product = null;
      message = " Ecxeption error!";
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        product = null;
        message = " SQL error!";
      }
    }

    return new Response<>(message, product);
  }

  @Override
  public Product setEnabledProduct(Long productId, String username){
    Connection conn = null;
    PreparedStatement preparedStatement;
    Product product = new Product();

    try {

      Class.forName(JDBC_DRIVER);

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      conn.setAutoCommit(false);

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

      conn.commit();

      rs.close();
      preparedStatement.close();

    } catch(SQLException se){
      se.printStackTrace();
      System.out.println("Rolling back data....");
      try{
        if(conn != null)
          conn.rollback();
      } catch(SQLException rollbackSe){
        rollbackSe.printStackTrace();
      }
      product = null;
    } catch(Exception se){
      se.printStackTrace();
      product = null;
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
        product = null;
      }
    }

    return product;
  }

  @Override
  public String deleteProduct(Long id, String username){
    Connection conn = null;
    PreparedStatement preparedStatement;
    String status = FAIL;
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

      sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ? AND USER_ID = ?";

      preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, id);
      preparedStatement.setLong(2, user_id);

      int effectedRow = preparedStatement.executeUpdate();

      if(effectedRow != 0)
        status = SUCCESS;

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


  private static boolean isResultSetEmpty(ResultSet rs) throws SQLException {
    return (!rs.isBeforeFirst() && rs.getRow() == 0);
  }

}

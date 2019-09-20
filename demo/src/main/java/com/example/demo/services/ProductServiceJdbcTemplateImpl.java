package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.rowMapper.AppUserRowMapper;
import com.example.demo.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@Service
@Profile("jdbcTemplate")
public class ProductServiceJdbcTemplateImpl implements ProductService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Product> listAllProducts() {
    String sql =  "SELECT PRODUCT.*, APP_USER.USER_NAME " +
                  "FROM PRODUCT, APP_USER " +
                  "WHERE PRODUCT.USER_ID = APP_USER.USER_ID";
    return jdbcTemplate.query(sql, new ProductRowMapper());
  }

  @Override
  public List<Product> listAllProductsByUser(String username) {
    String sql =  "SELECT PRODUCT.*, APP_USER.USER_NAME " +
                  "FROM PRODUCT, APP_USER " +
                  "WHERE PRODUCT.USER_ID = APP_USER.USER_ID AND APP_USER.USER_NAME = ?";
    return jdbcTemplate.query(sql, new Object[]{username}, new ProductRowMapper());
  }

  @Override
  public Product getProductById(Long id) {
    String sql =  "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";
    return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
  }

  @Override
  public Product saveOrUpdateProduct(Product product, String username) {

    String sql =  "SELECT * " +
                  "FROM APP_USER " +
                  "WHERE USER_NAME = ?";
    AppUser appUser = jdbcTemplate.queryForObject(sql, new Object[]{username}, new AppUserRowMapper());

    sql = "SELECT COUNT(PRODUCT.PRODUCT_ID) " +
          "FROM PRODUCT, APP_USER " +
          "WHERE PRODUCT.USER_ID = APP_USER.USER_ID AND APP_USER.USER_NAME = ?";

    Integer amount;
    if (appUser != null && appUser.getUserName() != null) {
      amount = jdbcTemplate.queryForObject(sql, new Object[]{appUser.getUserName()}, Integer.class);

      if (product.getProductId() == null) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        product.setEnabled(amount != null);

        final String insertSQL =  "INSERT INTO PRODUCT (NAME, JOB_TITLE, ADDRESS, TELEPHONE, EMAIL, WEBSITE, LANGUAGE, ABOUT, ENABLED, USER_ID) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            connection -> {
              PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
              preparedStatement.setString(1, product.getName());
              preparedStatement.setString(2, product.getJobTitle());
              preparedStatement.setString(3, product.getAddress());
              preparedStatement.setString(4, product.getTelephone());
              preparedStatement.setString(5, product.getEmail());
              preparedStatement.setString(6, product.getWebsite());
              preparedStatement.setString(7, product.getLanguage());
              preparedStatement.setString(8, product.getAbout());
              preparedStatement.setBoolean(9, product.isEnabled());
              preparedStatement.setLong(10, appUser.getUserId());
              return preparedStatement;
            },
            keyHolder);

        if (keyHolder.getKey() != null) {
          product.setProductId(keyHolder.getKey().longValue());
        }

      }
      else {
        sql = "UPDATE PRODUCT SET " +
              "NAME = ?, JOB_TITLE = ?, ADDRESS = ?, TELEPHONE = ?, EMAIL = ?, WEBSITE = ?, LANGUAGE = ?, ABOUT = ? " +
              "WHERE PRODUCT_ID = ?";
        jdbcTemplate.update(
            sql,
            product.getName(),
            product.getJobTitle(),
            product.getAddress(),
            product.getTelephone(),
            product.getEmail(),
            product.getWebsite(),
            product.getLanguage(),
            product.getAbout(),
            product.getProductId());
      }
    }

    return product;
  }

  @Override
  public Product setEnabledProduct(Long productId, String username) {

    String sql =  "SELECT * " +
                  "FROM APP_USER " +
                  "WHERE USER_NAME = ?";
    AppUser appUser = jdbcTemplate.queryForObject(sql, new Object[]{username}, new AppUserRowMapper());

    long user_id = -1;
    if (appUser != null && appUser.getUserId() != null)
      user_id = appUser.getUserId();

    sql = "UPDATE PRODUCT " +
          "SET ENABLED = ? " +
          "WHERE USER_ID = ? AND ENABLED = ?";
    jdbcTemplate.update(sql, false, user_id, true);

    sql = "UPDATE PRODUCT " +
          "SET ENABLED = ? " +
          "WHERE PRODUCT_ID = ?";
    jdbcTemplate.update(sql, true, productId);

    return null;
  }

  @Override
  public void deleteProduct(Long id) {

    String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
    jdbcTemplate.update(sql, id);

  }
}

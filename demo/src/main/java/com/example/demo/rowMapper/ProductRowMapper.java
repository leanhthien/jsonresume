package com.example.demo.rowMapper;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
  @Override
  public Product mapRow(ResultSet resultSet, int i) throws SQLException {
    Product product = new Product();
    AppUser appUser = new AppUser();
    product.setProductId(resultSet.getLong("product_id"));
    product.setName(resultSet.getString("name"));
    product.setJobTitle(resultSet.getString("job_title"));
    product.setAddress(resultSet.getString("address"));
    product.setTelephone(resultSet.getString("telephone"));
    product.setEmail(resultSet.getString("email"));
    product.setWebsite(resultSet.getString("website"));
    product.setLanguage(resultSet.getString("language"));
    product.setAbout(resultSet.getString("about"));
    product.setEnabled(resultSet.getBoolean("enabled"));
    appUser.setUserId(resultSet.getLong("user_id"));

    try {
      String user_name = resultSet.getString("user_name");
      appUser.setUserName(user_name);
    }
    catch (SQLException se) {
      //
    }

    product.setAppUser(appUser);
    return product;
  }
}

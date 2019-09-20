package com.example.demo.jdbc;

import com.example.demo.dao.AppUserDAO;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepositoryImpl implements JdbcProductRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private AppUserDAO appUserDAO;

  @Override
  public int count() {

    Integer amount =  jdbcTemplate.queryForObject("select count(*) from Product", Integer.class);

    if (amount != null)
      return amount;
    else
      return -1;
  }

  @Override
  public int save(Product product, String username) {

    return jdbcTemplate.update(
        "insert into product (about, address, user_id, email, job_title, language, name, telephone, website) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
        product.getAbout(),
        product.getAddress(),
        product.getAppUser().getUserId(),
        product.getEmail(),
        product.getJobTitle(),
        product.getLanguage(),
        product.getName(),
        product.getTelephone(),
        product.getWebsite());
  }

  @Override
  public int update(Product product) {

    return 0;
  }

  @Override
  public int deleteById(Long id) {
    return 0;
  }

  @Override
  public List<Product> findAll() {
    return null;
  }

  @Override
  public List<Product> findByUser(String name) {
    return null;
  }

  @Override
  public Optional<Product> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public String getNameById(Long id) {
    return null;
  }
}

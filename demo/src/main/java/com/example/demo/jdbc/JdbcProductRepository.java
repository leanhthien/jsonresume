package com.example.demo.jdbc;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface JdbcProductRepository {
  int count();

  int save(Product product, String username);

  int update(Product product);

  int deleteById(Long id);

  List<Product> findAll();

  List<Product> findByUser(String username);

  Optional<Product> findById(Long id);

  String getNameById(Long id);
}

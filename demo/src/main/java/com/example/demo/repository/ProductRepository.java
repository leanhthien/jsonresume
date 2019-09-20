package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = "SELECT * FROM PRODUCT WHERE user_id = ?1", nativeQuery = true)
  List<Product> findByUserId(Long user_id);

  @Query(value = "SELECT * FROM PRODUCT WHERE enabled = ?1 AND user_id = ?2", nativeQuery = true)
  Product findByEnabled(boolean enabled, Long user_id);
}

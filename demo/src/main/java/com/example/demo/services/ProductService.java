package com.example.demo.services;


import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {
  List<Product> listAllProducts();

  List<Product> listAllProductsByUser(String username);

  Product getProductById(Long id);

  Product saveOrUpdateProduct(Product product, String username);

  Product setEnabledProduct(Long productId, String username);

  void deleteProduct(Long id);
}
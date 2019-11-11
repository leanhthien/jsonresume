package com.example.demo.services;


import java.util.List;

import com.example.demo.entity.Product;
import com.example.demo.model.Response;

public interface ProductService {
  List<Product> listAllProducts();

  List<Product> listAllProductsByUser(String username);

  Product getProductById(Long id);

  Product getTopProduct(String username);

  Response<Product> saveOrUpdateProduct(Product product, String username);

  Product setEnabledProduct(Long productId, String username);

  String deleteProduct(Long id, String username);
}
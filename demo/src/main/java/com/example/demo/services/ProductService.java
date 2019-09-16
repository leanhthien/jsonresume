package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {
  List<Product> listAllProducts();

  Product getProductById(Integer id);

  Product saveOrUpdateProduct(Product product);

  void deleteProduct(Integer id);
}
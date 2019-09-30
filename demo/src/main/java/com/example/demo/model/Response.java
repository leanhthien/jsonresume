package com.example.demo.model;

import com.example.demo.entity.Product;

import java.util.List;

public class Response {
    Product product;
    List<Product> products;
    String message;

    public Response(String massage) {
        this.message = massage;
    }

    public Response(String massage, Product product) {
        this.message = massage;
        this.product = product;
    }

    public Response(String massage, List<Product> products) {
        this.message = massage;
        this.products = products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProducts() {
        return product;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}

package com.example.demo.services;

import com.example.demo.entity.Product;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("map")
public class ProductServiceImpl implements ProductService {

    private Map<Integer,Product> products;

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl() {
        // loadProducts();
        loadResumes();
    }

    @Override
    public List<Product> listAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProductById(Integer id) {
      return products.get(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
      if (product != null){
        if (product.getProductId() == null){
            product.setProductId(getNextKey());
        }
//        products.put(product.getProductId(), product);
        productRepository.save(product);

        return product;
      } else {
        throw new RuntimeException("Product can't be null");
      }
    }

    private Long getNextKey(){
      return Collections.max(products.keySet()) + 1L;
    }

    @Override
    public void deleteProduct(Integer id) {
      products.remove(id);
    }

    private void loadResumes(){
      products = new HashMap<>();

      Product product1 = new Product();
      product1.setProductId(1L);
      product1.setName("Albert");
      product1.setJobTitle("Computer Engineer");
      product1.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
      product1.setTelephone("(257) 563-7401");
      product1.setEmail("albert@ltd.com");
      product1.setWebsite("http://social.com/albert");
      product1.setLanguage("English");
      product1.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");
      
      products.put(1, product1);

      Product product2 = new Product();
      product2.setProductId(2L);
      product2.setName("Bob");
      product2.setJobTitle("Architecture");
      product2.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
      product2.setTelephone("(257) 563-7401");
      product2.setEmail("bob@ltd.com");
      product2.setWebsite("http://social.com/bob");
      product2.setLanguage("Japanese");
      product2.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");
      
      products.put(2, product2);

      Product product3 = new Product();
      product3.setProductId(3L);
      product3.setName("Charlie");
      product3.setJobTitle("Doctor");
      product3.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
      product3.setTelephone("(257) 563-7401");
      product3.setEmail("charlie@ltd.com");
      product3.setWebsite("http://social.com/charlie");
      product3.setLanguage("Chinese");
      product3.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");
      
      products.put(3, product3);

      Product product4 = new Product();
      product4.setProductId(4L);
      product4.setName("Dylan");
      product4.setJobTitle("Singer");
      product4.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
      product4.setTelephone("(257) 563-7401");
      product4.setEmail("dylan@ltd.com");
      product4.setWebsite("http://social.com/dylan");
      product4.setLanguage("Spanish");
      product4.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");
      
      products.put(4, product4);

      Product product5 = new Product();
      product5.setProductId(5L);
      product5.setName("Eugune");
      product5.setJobTitle("Developer");
      product5.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
      product5.setTelephone("(257) 563-7401");
      product5.setEmail("eugune@ltd.com");
      product5.setWebsite("http://social.com/eugune");
      product5.setLanguage("English");
      product5.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");

      products.put(5, product5);
  }

  
}
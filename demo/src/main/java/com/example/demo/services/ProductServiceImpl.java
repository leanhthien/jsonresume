package com.example.demo.services;

import com.example.demo.dao.AppUserDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;

import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Profile("map")
public class ProductServiceImpl implements ProductService {

    private Map<Integer,Product> products;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private EntityManager entityManager;

    public ProductServiceImpl() {
        // loadProducts();
        loadResumes();
    }

    @Override
    public List<Product> listAllProducts() {
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public List<Product> listAllProductsByUser(String username) {
      AppUser appUser = this.appUserDAO.findUserAccount(username);
      List<Product> products = productRepository.findByUserId(appUser.getUserId());
      if (products.isEmpty())
        return new ArrayList<>();
      else
        return products;
    }

  @Override
    public Product getProductById(Long id) {
      Optional<Product> product = productRepository.findById(id);
      return product.orElse(null);
    }

    @Override
    public Product saveOrUpdateProduct(Product product, String username) {
      if (product != null){
//        if (product.getProductId() == null){
//            product.setProductId(getNextKey());
//        }

        AppUser appUser = this.appUserDAO.findUserAccount(username);
        product.setAppUser(appUser);
        appUser.getProducts().add(product);

        List<Product> products = productRepository.findByUserId(appUser.getUserId());
        if (products.isEmpty())
          product.setEnabled(true);

        productRepository.save(product);

        return product;

      } else {
        throw new RuntimeException("Product can't be null");
      }
    }

    @Override
    public Product setEnabledProduct(Long productId, String username) {

      AppUser appUser = this.appUserDAO.findUserAccount(username);
      Product currentProduct = productRepository.findByEnabled(true, appUser.getUserId());
      if (currentProduct != null) {
        currentProduct.setEnabled(false);
        productRepository.save(currentProduct);
      }

      Product newProduct = productRepository.findById(productId).orElse(null);
      if (newProduct != null)
        newProduct.setEnabled(true);
      productRepository.save(newProduct);
      return newProduct;
    }

  private Long getNextKey(){
      return Collections.max(products.keySet()) + 1L;
    }

    @Override
    public String deleteProduct(Long id) {
      productRepository.deleteById(id);
      return "Success!";
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
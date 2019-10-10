package com.example.demo.controllers.springMVC;

import com.example.demo.dao.AppUserDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.entity.UserRole;
import com.example.demo.jdbc.JdbcProductRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

//@Controller
//@RequestMapping(path="/demo")
public class TestController {

//    private ProductService productService;
//
//    @Autowired
//    public void setProductService(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserRoleRepository roleRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private AppUserDAO appUserDAO;
//
//    @Autowired
//    @Qualifier("jdbcProductRepositoryImpl")
//    private JdbcProductRepository jdbcProductRepository;
//
//    @GetMapping(path="/allUser")
//    public @ResponseBody Iterable<AppUser> getAllUsers() {
//
//        return userRepository.findAll();
//    }
//
//    @GetMapping(path="/allRole")
//    public @ResponseBody Iterable<UserRole> getAllRoles() {
//
//        return roleRepository.findAll();
//    }
//
//    @GetMapping(path="/currentLogin")
//    public @ResponseBody AppUser getCurrentUser( Principal principal) {
//        return this.appUserDAO.findUserAccount(principal.getName());
//    }
//
//    @GetMapping(path="/test")
//    public @ResponseBody List<Product> getEnabledProduct() {
//
//        return productService.listAllProducts();
//    }
//
//    @GetMapping(path="/testCount")
//    public @ResponseBody Integer countProduct() {
//
//        return jdbcProductRepository.count();
//    }

}
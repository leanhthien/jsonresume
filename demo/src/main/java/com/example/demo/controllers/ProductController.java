package com.example.demo.controllers;

import com.example.demo.entity.Product;
import com.example.demo.jdbc.JdbcProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    @Qualifier("jdbcProductRepositoryImpl")
    private JdbcProductRepository jdbcProductRepository;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products/user")
    public String listUserProduct(Model model, Principal principal) {
        model.addAttribute("products", productService.listAllProductsByUser(principal.getName()));
        return "resume/resumes";
    }

    @RequestMapping("/products/all")
    public String listProduct(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return "resume/allResumes";
    }

    @RequestMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "resume/resume";
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "resume/resumeform";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "resume/resumeform";
    }

    @RequestMapping("view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "resume/resume";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Product product, Principal principal) {
        Product savedProduct = productService.saveOrUpdateProduct(product, principal.getName());
        return "redirect:/product/" + savedProduct.getProductId();
    }

    @RequestMapping("/product/set_enabled/{id}")
    public String setEnabled(@PathVariable Long id, Principal principal) {
        productService.setEnabledProduct(id, principal.getName());
        return "redirect:/products/user";
    }

    @RequestMapping("/product/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/user";
    }

}
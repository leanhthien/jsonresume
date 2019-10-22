package com.example.demo.controllers.springMVC;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Profile("mvc")
@Controller
public class ProductController {

    private ProductService productService;

//    @Autowired
//    @Qualifier("jdbcProductRepositoryImpl")
//    private JdbcProductRepository jdbcProductRepository;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products/user")
    public String listUserProduct(Model model,
                                  Principal principal,
                                  @ModelAttribute("error") String error,
                                  HttpServletRequest request,
                                  RedirectAttributes rm) {
        List<Product> products = productService.listAllProductsByUser(principal.getName());
        if (products != null) {
            model.addAttribute("products", products);
            model.addAttribute("error", error);
            return "resume/resumes";
        }
        else
            return WebUtils.redirect(request, rm).orElse("/");
    }

    @RequestMapping("/products/all")
    public String listProduct(Model model,
                              @ModelAttribute("error") String error,
                              HttpServletRequest request,
                              RedirectAttributes rm) {
        List<Product> products = productService.listAllProducts();
        if (products != null) {
            model.addAttribute("products", productService.listAllProducts());
            model.addAttribute("error", error);
            return "resume/allResumes";
        }
        else
            return WebUtils.redirect(request, rm).orElse("/");

    }

    @RequestMapping("/product/{id}")
    public String getProduct(@PathVariable Long id,
                             Model model,
                             Principal principal,
                             HttpServletRequest request,
                             RedirectAttributes rm) {
        Product product = productService.getProductById(id);
        if (product != null) {
            if(principal.getName().equals(product.getAppUser().getUserName()))
                model.addAttribute("isEditable",true);
            model.addAttribute("product", product);
            return "resume/resume";
        }
        else
            return WebUtils.redirect(request, rm).orElse("/");
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "resume/resumeForm";
    }

    @RequestMapping("/product/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model,
                       HttpServletRequest request,
                       RedirectAttributes rm) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "resume/resumeForm";
        }
        else
            return WebUtils.redirect(request, rm).orElse("/");

    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "resume/resume";
        }
        else {
            return "403Page";
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Product product,
                                      Model model,
                                      Principal principal,
                                      HttpServletRequest request,
                                      RedirectAttributes rm) {
        Product savedProduct = productService.saveOrUpdateProduct(product, principal.getName());
        if (savedProduct != null)
            return "redirect:/product/" + savedProduct.getProductId();
        else
            return WebUtils.redirect(request, rm).orElse("/");
    }

    @RequestMapping("/product/set_enabled/{id}")
    public String setEnabled(@PathVariable Long id,
                             Principal principal,
                             HttpServletRequest request,
                             RedirectAttributes rm) {
        Product product = productService.setEnabledProduct(id, principal.getName());
        if (product != null)
            return "redirect:/products/user";
        else
            return WebUtils.redirect(request, rm).orElse("/");

    }

    @RequestMapping("/product/delete/{id}")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request,
                         RedirectAttributes rm) {
        String status = productService.deleteProduct(id);
        if (status.equals("Success"))
            return "redirect:/products/user";
        else
            return WebUtils.redirect(request, rm).orElse("/");
    }

}
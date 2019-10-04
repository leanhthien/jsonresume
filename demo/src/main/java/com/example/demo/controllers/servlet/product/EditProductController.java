package com.example.demo.controllers.servlet.product;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="editProduct", urlPatterns = "/servlet/product/edit")
public class EditProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            long id = Long.parseLong(request.getParameter("id"));
            Product product = productService.getProductById(id);

            request.setAttribute("product", product);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/resume/editResumeForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            long   productId = Long.parseLong(request.getParameter("productId"));
            String name = request.getParameter("name");
            String job_title = request.getParameter("jobTitle");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            String website = request.getParameter("website");
            String language = request.getParameter("language");
            String about = request.getParameter("about");

            Product editProduct = new Product(name, job_title, address, telephone, email, website, language, about);
            editProduct.setProductId(productId);

            editProduct = productService.saveOrUpdateProduct(editProduct, "dbadmin1");

            response.sendRedirect("detail?id="+ editProduct.getProductId());
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}

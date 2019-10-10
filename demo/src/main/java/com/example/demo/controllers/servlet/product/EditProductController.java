package com.example.demo.controllers.servlet.product;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Product;
import com.example.demo.services.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.utils.Const.LOGIN_SESSION;

@WebServlet(name="editProduct", urlPatterns = "/servlet/product/edit")
public class EditProductController extends HttpServlet {

    private ProductService productService;

    private UserService userService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            long id = Long.parseLong(request.getParameter("id"));
            String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);
            Product product = productService.getProductById(id);

            if (product != null) {
                if (username.equals(product.getAppUser().getUserName())) {
                    request.setAttribute("product", product);

                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/resume/editResumeForm.jsp");
                    dispatcher.forward(request, response);
                }
                else {
                    response.sendRedirect("error");
                }
            }
            else {
                response.sendRedirect("error");
            }
        } catch (Exception e) {
            response.sendRedirect("error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);
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

            editProduct = productService.saveOrUpdateProduct(editProduct, username);

            response.sendRedirect("detail?id="+ editProduct.getProductId());
        } catch (Exception e) {
            response.sendRedirect("error");
        }
    }
}

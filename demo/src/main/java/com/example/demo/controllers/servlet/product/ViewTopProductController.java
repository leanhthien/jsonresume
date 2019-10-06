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

import static com.example.demo.utils.Const.LOGIN_SESSION;

@WebServlet(name="viewDetailTopProduct", urlPatterns = "/servlet/view")
public class ViewTopProductController extends HttpServlet {
    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String username = request.getParameter("name");

            Product product = productService.getTopProduct(username);

            request.setAttribute("product", product);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/resume/resume.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
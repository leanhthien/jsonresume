package com.example.demo.controllers.servlet.jsp.product;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile("api")
@WebServlet(name="viewDetailTopProduct", urlPatterns = "/servlet/view")
public class ViewTopProductController extends HttpServlet {

    private Gson gson = new Gson();

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String username = request.getParameter("name");

            Product product = productService.getTopProduct(username);

            if (product != null) {
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resume/resume.jsp");
                dispatcher.forward(request, response);
            }
            else {
                response.sendRedirect("error");
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

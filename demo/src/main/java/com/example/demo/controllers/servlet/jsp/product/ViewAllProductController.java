package com.example.demo.controllers.servlet.jsp.product;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import org.springframework.context.annotation.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.demo.utils.ConstUtils.ERROR_RESPONSE;

@Profile("api")
@WebServlet(name="listAllProduct", urlPatterns = "/servlet/product/all")
public class ViewAllProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Product> products = productService.listAllProducts();

            if(products == null)
                request.setAttribute(ERROR_RESPONSE, "Something went wrong");
            else
                request.setAttribute("products", products);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resume/allResumes.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

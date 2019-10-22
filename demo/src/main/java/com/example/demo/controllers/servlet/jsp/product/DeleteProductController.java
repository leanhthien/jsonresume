package com.example.demo.controllers.servlet.jsp.product;

import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.utils.ConstUtils.ERROR_RESPONSE;

@Profile("api")
@WebServlet(name="deleteProduct", urlPatterns = "/servlet/product/delete")
public class DeleteProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            long productId = Long.parseLong(request.getParameter("id"));
            String status = productService.deleteProduct(productId);

            if (status.equals("Fail"))
                request.setAttribute(ERROR_RESPONSE,"Can not delete resume!");

            response.sendRedirect("user");

        } catch (Exception e) {
            response.sendRedirect("error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

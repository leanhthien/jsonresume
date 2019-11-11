package com.example.demo.controllers.servlet.api.product;

import com.example.demo.entity.Product;
import com.example.demo.model.Response;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.APIUtils;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="listAllProduct", urlPatterns = "/api/product/all")
public class ViewAllProductAPI extends HttpServlet {

    private Gson gson;

    private UserService userService;

    private ProductService productService;

    public void init() {
        gson = new Gson();
        userService = new UserServiceJdbcImpl();
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result;

        try {
            List<Product> products = productService.listAllProducts();

            if (products == null) {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot found products!"));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            else
                result = this.gson.toJson(new Response<>(SUCCESS, products));

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            result = this.gson.toJson(new Response<>(FAIL, COMMON_ERROR));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        APIUtils.printResult(response, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

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

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="viewDetailProduct", urlPatterns = "/api/product/detail")
public class ViewDetailProductAPI extends HttpServlet {

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
            long id = Long.parseLong(request.getParameter("id"));
            Product product = productService.getProductById(id);

            if (product != null)
                result = this.gson.toJson(new Response<>(SUCCESS, product));
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot found product!"));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

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

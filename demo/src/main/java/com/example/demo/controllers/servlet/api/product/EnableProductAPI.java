package com.example.demo.controllers.servlet.api.product;

import com.example.demo.entity.Product;
import com.example.demo.model.Response;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
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
import static com.example.demo.utils.ConstUtils.FAIL;

@Profile("api")
@WebServlet(name="enableProduct", urlPatterns = "/api/product/enable")
public class EnableProductAPI extends HttpServlet {

    private Gson gson;

    private ProductService productService;

    public void init() {
        gson = new Gson();
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result;
        try {
            long productId = Long.parseLong(request.getParameter("id"));
            String username = request.getParameter("username");
            Product product = productService.setEnabledProduct(productId, username);

            if (product != null)
                result = this.gson.toJson(new Response<>(SUCCESS, product));
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot enable product!"));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            result = this.gson.toJson(new Response<>(FAIL, COMMON_ERROR));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        APIUtils.printResult(response, result);
    }
}

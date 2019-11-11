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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="newProduct", urlPatterns = "/api/product/new")
public class NewProductAPI extends HttpServlet {

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

            this.log("Request: " + Arrays.toString(request.getParameterValues("params")));

            StringBuilder sb = new StringBuilder();
            Product newProduct;

            try (BufferedReader reader = request.getReader()) {

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }

                newProduct = this.gson.fromJson(sb.toString(), Product.class);
            }

            String username = request.getParameter("username");

            Response<Product> data = productService.saveOrUpdateProduct(newProduct, username);

            if (data.getData() != null)
                result = this.gson.toJson(new Response<>(SUCCESS, data.getData()));
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot create product!"));
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
}

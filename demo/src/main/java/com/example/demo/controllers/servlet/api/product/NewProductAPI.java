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

        String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);

        String result;
        try {

            String name = request.getParameter("name");
            String job_title = request.getParameter("jobTitle");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            String website = request.getParameter("website");
            String language = request.getParameter("language");
            String about = request.getParameter("about");
            String workExperience = request.getParameter("workExperience");

            Product newProduct = new Product(name, job_title, address, telephone, email, website, language, about, workExperience);

            newProduct = productService.saveOrUpdateProduct(newProduct, username);

            if (newProduct != null)
                result = this.gson.toJson(new Response<>(SUCCESS, newProduct));
            else {
                result = this.gson.toJson(new Response<>(FAIL, null));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot create product!");
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

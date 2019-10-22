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
@WebServlet(name="editProduct", urlPatterns = "/api/product/edit")
public class EditProductAPI extends HttpServlet {

    private Gson gson;
    private ProductService productService;
    private String result;

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

        try {
            String username = request.getParameter("username");
            long   productId = Long.parseLong(request.getParameter("productId"));
            String name = request.getParameter("name");
            String job_title = request.getParameter("jobTitle");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            String email = request.getParameter("email");
            String website = request.getParameter("website");
            String language = request.getParameter("language");
            String about = request.getParameter("about");
            String workExperience = request.getParameter("workExperience");
            boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
            Product editProduct = new Product(productId, name, job_title, address, telephone, email, website, language, about, workExperience, enabled);
            editProduct.setProductId(productId);

            editProduct = productService.saveOrUpdateProduct(editProduct, username);

            if (editProduct != null)
                result = this.gson.toJson(new Response<>(SUCCESS, editProduct));
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot edit product"));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            result = this.gson.toJson(new Response<>(FAIL, null));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        APIUtils.printResult(response, result);
    }
}

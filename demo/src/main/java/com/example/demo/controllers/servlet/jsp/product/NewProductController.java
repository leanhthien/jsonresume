package com.example.demo.controllers.servlet.jsp.product;

import com.example.demo.entity.Product;
import com.example.demo.model.Response;
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

import static com.example.demo.utils.ConstUtils.LOGIN_SESSION;

@Profile("api")
@WebServlet(name="newProduct", urlPatterns = "/servlet/product/new")
public class NewProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resume/newResumeForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);

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

            Response<Product> data = productService.saveOrUpdateProduct(newProduct, username);

            response.sendRedirect("detail?id="+ data.getData().getProductId());
        } catch (Exception e) {
            response.sendRedirect("error");
        }
    }
}

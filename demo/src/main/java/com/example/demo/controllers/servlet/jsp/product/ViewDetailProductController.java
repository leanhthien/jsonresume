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

import static com.example.demo.utils.ConstUtils.LOGIN_SESSION;

@Profile("api")
@WebServlet(name="viewDetailProduct", urlPatterns = "/servlet/product/detail")
public class ViewDetailProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);
            long id = Long.parseLong(request.getParameter("id"));
            Product product = productService.getProductById(id);

            if (product != null) {
                request.setAttribute("product", product);
                if (product.getAppUser().getUserName().equals(username))
                    request.setAttribute("isEditable", true);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resume/resume.jsp");
                dispatcher.forward(request, response);
            }
            else
                response.sendRedirect("error");

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

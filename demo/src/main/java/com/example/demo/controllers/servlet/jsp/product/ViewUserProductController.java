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
import static com.example.demo.utils.ConstUtils.LOGIN_SESSION;

@Profile("api")
@WebServlet(name="listUserProduct", urlPatterns = "/servlet/product/user")
public class ViewUserProductController extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String) request.getSession(false).getAttribute(LOGIN_SESSION);

        if (!username.isEmpty()) {
            try {
                List<Product> products = productService.listAllProductsByUser(username);

                if (products == null) {
                    request.setAttribute(ERROR_RESPONSE, "Something went wrong!");
                }
                else {
                    String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                    request.setAttribute("domain", domain);
                    request.setAttribute("products", products);
                }

                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/resume/resumes.jsp");
                dispatcher.forward(request, response);

            } catch (Exception e) {
                this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
                response.sendRedirect("error");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

package com.example.demo.controllers.servlet.user;

import com.example.demo.entity.AppUser;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="registration", urlPatterns = "/servlet/registration")
public class NewUserController extends HttpServlet {

    private UserService userService;

    public void init() {
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/registration.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String userName = request.getParameter("userName");
            String encryptedPassword = request.getParameter("encryptedPassword");
            String retypePassword = request.getParameter("retypePassword");

            if (encryptedPassword.equals(retypePassword)) {
                AppUser appUser = new AppUser(userName, encryptedPassword);
                userService.saveOrUpdateUser(appUser);
            }

            response.sendRedirect("home");
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}

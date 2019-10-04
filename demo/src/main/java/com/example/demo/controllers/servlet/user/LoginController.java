package com.example.demo.controllers.servlet.user;

import com.example.demo.entity.AppUser;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.EncrytedPasswordUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="login", urlPatterns = "/servlet/login")
public class LoginController extends HttpServlet {

    private UserService userService;

    public void init() {
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/login.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String userName = request.getParameter("userName");
            String encryptedPassword = EncrytedPasswordUtils.encrytePassword(request.getParameter("encryptedPassword"));

            AppUser appUser = userService.getUserByName(userName);

            if (appUser != null && encryptedPassword.equals(appUser.getEncryptedPassword())) {

                HttpSession session = request.getSession();

                session.setAttribute("userName", userName);

                request.setAttribute("appUser", appUser);

                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/resume/resumes.jsp");
                dispatcher.forward(request, response);
            }


            response.sendRedirect("home");
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}

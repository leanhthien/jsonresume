package com.example.demo.controllers.servlet.user;

import com.example.demo.entity.AppUser;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.EncrytedPasswordUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static com.example.demo.utils.Const.ERROR_RESPONSE;
import static com.example.demo.utils.Const.LOGIN_SESSION;

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
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String userName = request.getParameter("username");
            String rawPassword = request.getParameter("password");

            AppUser appUser = userService.getUserByName(userName);

            if (appUser != null && EncrytedPasswordUtils.isPasswordMatch(rawPassword, appUser.getEncryptedPassword())) {

                HttpSession oldSession = request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession newSession = request.getSession(true);

                newSession.setMaxInactiveInterval(5*60);

                Cookie message = new Cookie("message", "JsonResume");

                response.addCookie(message);

                newSession.setAttribute(LOGIN_SESSION, userName);

                response.sendRedirect("product/user");
            }
            else {
                request.setAttribute(ERROR_RESPONSE,"Username or password is not correct!");
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("/");
        }
    }
}

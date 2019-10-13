package com.example.demo.controllers.servlet.user;

import com.example.demo.entity.AppUser;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
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

@WebServlet(name="registration", urlPatterns = "/servlet/registration")
public class RegistrationController extends HttpServlet {

    private UserService userService;

    public void init() {
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(LOGIN_SESSION) == null) {
            try {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/registration.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
                response.sendRedirect("error");
            }
        }
        else {
            response.sendRedirect("product/user");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String retypePassword = request.getParameter("retypePassword");

            if (password.equals(retypePassword)) {

                String encryptedPassword = EncrytedPasswordUtils.encryptPassword(password);

                AppUser appUser = new AppUser(userName, encryptedPassword);

                AppUser checkUser = userService.getUserByName(userName);

                if (checkUser == null) {
                    userService.saveOrUpdateUser(appUser);

                    HttpSession oldSession = request.getSession(false);

                    if (oldSession != null) {
                        oldSession.invalidate();
                    }

                    HttpSession newSession = request.getSession(true);

                    newSession.setMaxInactiveInterval(5*60);

                    newSession.setAttribute(LOGIN_SESSION, userName);

                    Cookie loginCookie = new Cookie("user", userName);

                    loginCookie.setMaxAge(30*60);

                    response.addCookie(loginCookie);

                    response.sendRedirect("product/user");
                }
                else {
                    request.setAttribute(ERROR_RESPONSE,"User already exist!");
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/registration.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else {
                request.setAttribute(ERROR_RESPONSE,"Password and retype password must be the same!");
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/registration.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("error");
        }
    }
}

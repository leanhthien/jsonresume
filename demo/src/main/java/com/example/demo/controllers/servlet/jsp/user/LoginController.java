package com.example.demo.controllers.servlet.jsp.user;

import com.example.demo.entity.AppUser;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.EncrytedPasswordUtils;
import org.springframework.context.annotation.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import static com.example.demo.utils.ConstUtils.ERROR_RESPONSE;
import static com.example.demo.utils.ConstUtils.LOGIN_SESSION;

@Profile("api")
@WebServlet(name="login", urlPatterns = "/servlet/login")
public class LoginController extends HttpServlet {

    private UserService userService;

    private Optional<String> prevLink;

    public void init() {
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        prevLink = Optional.ofNullable(request.getHeader("Referer"));

        this.log("Previous url is: " + prevLink.toString());

        try {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/user/login.html");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("error");
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

                newSession.setAttribute(LOGIN_SESSION, userName);

                Cookie loginCookie = new Cookie("user", userName);

                loginCookie.setMaxAge(30*60);

                response.addCookie(loginCookie);

                if (prevLink.toString().contains("registration")
                        || prevLink.toString().contains("login")
                        || prevLink.toString().contains("logout")
                        || prevLink.toString().contains("empty")) {
                    response.sendRedirect("product/user");
                }
                else {
                    response.sendRedirect(prevLink.orElse("home"));
                }

            }
            else {
                request.setAttribute(ERROR_RESPONSE,"Username or password is not correct!");
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/user/login.html");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("error");
        }
    }


}

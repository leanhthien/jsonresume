package com.example.demo.controllers.servlet.jsp.user;

import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Profile("api")
@WebServlet(name="logout", urlPatterns = "/servlet/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Cookie loginCookie = null;
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        loginCookie = cookie;
                        break;
                    }
                }
            }

            if(loginCookie != null){
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
            }

            HttpSession session = request.getSession(false);

            if(session != null){
                this.log("Remove session");
                session.invalidate();
            }
            response.sendRedirect("home");

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

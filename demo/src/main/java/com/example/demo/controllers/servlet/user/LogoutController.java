package com.example.demo.controllers.servlet.user;

import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="logout", urlPatterns = "/servlet/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if(session != null){
                this.log("Remove session");
                session.invalidate();
                response.sendRedirect("home");
                return;
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["+ Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

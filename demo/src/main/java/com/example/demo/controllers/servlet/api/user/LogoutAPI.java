package com.example.demo.controllers.servlet.api.user;

import com.example.demo.model.Response;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.APIUtils;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="logout", urlPatterns = "/api/logout")
public class LogoutAPI extends HttpServlet {

    private Gson gson;

    private UserService userService;

    public void init() {
        gson = new Gson();
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result;
        try {

            String token = request.getParameter("token");
            String status = userService.deleteToken(token);
            if (status.equals(SUCCESS))
                result = this.gson.toJson(new Response<>(SUCCESS, "Logout Success!"));
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Cannot logout!"));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            result = this.gson.toJson(new Response<>(FAIL, COMMON_ERROR));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        APIUtils.printResult(response, result);
    }
}

package com.example.demo.controllers.servlet.api.user;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.model.Response;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.APIUtils;
import com.example.demo.utils.EncrytedPasswordUtils;
import com.example.demo.utils.RandomString;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="login", urlPatterns = "/api/login")
public class LoginAPI extends HttpServlet {

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

            String userName = request.getParameter("username");
            String rawPassword = request.getParameter("password");

            AppUser appUser = userService.getUserByName(userName);

            if (appUser != null && EncrytedPasswordUtils.isPasswordMatch(rawPassword, appUser.getEncryptedPassword())) {

                Token token = userService.setToken(appUser);

                if (token != null)
                    result = this.gson.toJson(new Response<>(SUCCESS, token));
                else {
                    result = this.gson.toJson(new Response<>(FAIL, "Cannot create token!"));
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Username or password is not correct!"));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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

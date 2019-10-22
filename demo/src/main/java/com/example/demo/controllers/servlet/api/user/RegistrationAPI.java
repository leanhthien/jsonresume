package com.example.demo.controllers.servlet.api.user;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Token;
import com.example.demo.model.Response;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.APIUtils;
import com.example.demo.utils.EncrytedPasswordUtils;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.example.demo.utils.ConstUtils.*;
import static com.example.demo.utils.ConstUtils.FAIL;

@Profile("api")
@WebServlet(name="registration", urlPatterns = "/api/registration")
public class RegistrationAPI extends HttpServlet {

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
            String password = request.getParameter("password");
            String retypePassword = request.getParameter("retypePassword");

            if (password.equals(retypePassword)) {

                String encryptedPassword = EncrytedPasswordUtils.encryptPassword(password);

                AppUser appUser = new AppUser(userName, encryptedPassword);

                AppUser checkUser = userService.getUserByName(userName);

                if (checkUser == null) {
                    userService.saveOrUpdateUser(appUser);
                    Token token = userService.setToken(appUser);

                    if (token != null)
                        result = this.gson.toJson(new Response<>(SUCCESS, token));
                    else {
                        result = this.gson.toJson(new Response<>(FAIL, "Cannot create token!"));
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }
                else {
                    result = this.gson.toJson(new Response<>(FAIL, "User already exist!"));
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            else {
                result = this.gson.toJson(new Response<>(FAIL, "Password and retype password must be the same!"));
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

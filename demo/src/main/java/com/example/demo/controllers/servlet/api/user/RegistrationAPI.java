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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

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
        String errorMessage = "";
        Token token = null;
        try {

            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String retypePassword = request.getParameter("retypePassword");

            if (password != null && password.equals(retypePassword)) {

                String encryptedPassword = EncrytedPasswordUtils.encryptPassword(password);

                AppUser appUser = new AppUser(userName, encryptedPassword);

                AppUser checkUser = userService.getUserByName(userName);

                if (checkUser == null) {
                    AppUser user = userService.saveOrUpdateUser(appUser);

                    if (user != null) {
                        token = userService.setToken(appUser);

                        if (token == null)
                            errorMessage = "Cannot create token!";
                    }
                    else
                        errorMessage = "Cannot create new user!";

                }
                else {
                    errorMessage = "User already exists!";
                }
            }
            else {
                errorMessage = "Password and retype password must be the same!";
            }

            this.log("Error message: "+ errorMessage);
            if (!errorMessage.isEmpty()) {
                this.log("Trigger data fail!");
                result = this.gson.toJson(new Response<>(FAIL, errorMessage));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            else {
                result = this.gson.toJson(new Response<>(SUCCESS, token));
            }

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);

            this.log("Trigger catch fail!");
            result = this.gson.toJson(new Response<>(FAIL, COMMON_ERROR));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        APIUtils.printResult(response, result);
    }
}

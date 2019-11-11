package com.example.demo.controllers.servlet.api.user;


import com.example.demo.model.Response;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.example.demo.utils.APIUtils;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.utils.ConstUtils.*;

@Profile("api")
@WebServlet(name="token", urlPatterns = "/api/token")
public class TokenAPI extends HttpServlet {

    private Gson gson;

    private UserService userService;

    public void init() {
        gson = new Gson();
        userService = new UserServiceJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result;
        try {

            String token = request.getHeader("Authorization");

            Response<String> validateResponse =  userService.validateToken(token);
            result = this.gson.toJson(new Response<>(validateResponse.getMessage(), validateResponse.getData()));
            if(validateResponse.getMessage().equals(FAIL))
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            this.log("Error in [" + this.getClass().getSimpleName() + "] at method ["
                    + Thread.currentThread().getStackTrace()[1].getMethodName() + "]", e);
            result = this.gson.toJson(new Response<>(FAIL, COMMON_ERROR));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        APIUtils.printResult(response, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

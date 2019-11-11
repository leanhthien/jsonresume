package com.example.demo.utils;

import com.example.demo.model.Response;
import com.example.demo.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.example.demo.utils.ConstUtils.FAIL;

public class APIUtils {

    public static Response<String> isValidUser(String token, UserService userService, ServletContext context) {

        Response<String> result;
        try {
            if (token != null && !token.isEmpty()) {
                result = userService.validateToken(token);
            }
            else
                result = new Response<>(FAIL, "Token is empty!");
        }
        catch (Exception e) {
            context.log("Error in validate token!");
            result = new Response<>(FAIL, "Cannot validate token!");
        }
        return result;
    }

    public static void printResult(HttpServletResponse response, String result) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(result);
        out.flush();
//        out.write(result);
    }

}

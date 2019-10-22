package com.example.demo.utils;

import com.example.demo.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.example.demo.utils.ConstUtils.SUCCESS;

public class APIUtils {

    public static boolean isValidUser(HttpServletRequest request, UserService userService, ServletContext context) {

        try {
            String token = request.getParameter("token");
            String userId = request.getParameter("userId");
            if (!token.isEmpty() && !userId.isEmpty()) {
                String status = userService.validateToken(token, Long.parseLong(userId));
                return status.equals(SUCCESS);
            }
        }
        catch (Exception e) {
            context.log("Error in validate token!");
        }

        return false;
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

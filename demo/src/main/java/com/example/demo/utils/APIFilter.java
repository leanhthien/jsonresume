package com.example.demo.utils;

import com.example.demo.model.Response;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceJdbcImpl;
import com.google.gson.Gson;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.utils.ConstUtils.FAIL;

@Profile("api")
@WebFilter("/*")
public class APIFilter implements Filter {

    private Gson gson;

    private ServletContext context;

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.userService = new UserServiceJdbcImpl();
        this.gson = new Gson();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        addCorsHeader(response);
        this.context.log("Trigger filter of api");

        if (request.getRequestURI().contains("api/product")) {
            if (APIUtils.isValidUser(request, userService, context)) {
                this.context.log("Trigger valid token");
                filterChain.doFilter(request, response);
            }
            else {
                this.context.log("Trigger invalid token");
                String result = this.gson.toJson(new Response<>(FAIL, "Invalid token"));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                APIUtils.printResult(response, result);
            }
        }
        else {
            filterChain.doFilter(request, response);
        }



    }

    @Override
    public void destroy() { }

    private void addCorsHeader(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }
}

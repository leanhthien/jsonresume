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
import static com.example.demo.utils.ConstUtils.SUCCESS;

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

        if (request.getRequestURI().contains("api/product")) {

            String token = request.getHeader("Authorization");
            Response<String> validateResponse = APIUtils.isValidUser(token, userService, context);

            this.context.log("URI: " + request.getRequestURI());
            this.context.log("Authorization: " + token);

            if (validateResponse.getMessage().equals(SUCCESS)) {
                this.context.log("Validate success");
            }
            else {
                this.context.log("Validate fail!");
            }
                filterChain.doFilter(request, response);

//            if (validateResponse.getMessage().equals(SUCCESS)) {
//                filterChain.doFilter(request, response);
//            }
//            else {
//                String result = this.gson.toJson(new Response<>(FAIL, validateResponse.getData()));
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                APIUtils.printResult(response, result);
//            }
        }
        else {
            filterChain.doFilter(request, response);
        }

//        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() { }

    private void addCorsHeader(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }
}

package com.example.demo.simpleLogin;

import java.util.*;

public class SecurityConfig {

    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

    static {
        init();
    }

    private static void init() {

        // Configure For "EMPLOYEE" Role.
        List<String> urlPatterns1 = new ArrayList<String>();

        urlPatterns1.add("/userInfo");
        urlPatterns1.add("/employeeTask");

        mapConfig.put(ROLE_EMPLOYEE, urlPatterns1);

        // Configure For "MANAGER" Role.
        List<String> urlPatterns2 = new ArrayList<String>();

        urlPatterns2.add("/userInfo");
        urlPatterns2.add("/managerTask");

        mapConfig.put(ROLE_MANAGER, urlPatterns2);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}

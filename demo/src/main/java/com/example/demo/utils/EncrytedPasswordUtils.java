package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {

    // Encrypt Password with BCryptPasswordEncoder
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean isPasswordMatch(String rawPassword, String encodePassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodePassword);
    }


    public static void main(String[] args) {
        String password = "123";
        String encrytedPassword = encryptPassword(password);

        System.out.println("Encrypted Password: " + encrytedPassword);
    }

}

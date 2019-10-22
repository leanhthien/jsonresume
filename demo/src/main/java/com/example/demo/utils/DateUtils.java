package com.example.demo.utils;

import java.time.LocalDateTime;


public class DateUtils {

    public static Boolean isValid(LocalDateTime createdAt) {
        if (createdAt != null) {
            LocalDateTime expireTime = createdAt.plusDays(1);
            LocalDateTime now = LocalDateTime.now();

            return now.isBefore(expireTime);
        }
        return false;
    }
}

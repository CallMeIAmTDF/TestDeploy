package com.huce.edu.utils;

import java.util.Random;

public class GenerateOtpUtil {
    public static String create(int n) {
        StringBuilder sb = new StringBuilder(n);
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}

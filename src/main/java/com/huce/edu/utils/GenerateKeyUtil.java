package com.huce.edu.utils;


import java.util.Random;

public class GenerateKeyUtil {
        public static String generate() {
            Random random = new Random();
            byte[] bytes = new byte[64];
            random.nextBytes(bytes);
            return bytesToHex(bytes);
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int value = b & 0xFF;
                sb.append(String.format("%02x", value));
            }
            return sb.toString();
        }
}

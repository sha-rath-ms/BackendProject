package com.example.backend.extract;

import java.util.Random;

public class GenerateOtp {
    public static int getOtp() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}

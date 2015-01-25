package com.comicszone.managedbeans.util.passwordcreators;

import java.util.Random;

public class SimplePasswordCreator implements IPasswordCreator {

    private final String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String createPassword(int length) {
        StringBuilder builder = new StringBuilder(length);
        Random random = new Random();
        int validCharsLength = validChars.length();
        
        for (int i = 0; i < builder.capacity(); i++){
            char nextChar = validChars.charAt(random.nextInt(validCharsLength));
            builder.append(nextChar);
        }
        
        return builder.toString();
    }

}

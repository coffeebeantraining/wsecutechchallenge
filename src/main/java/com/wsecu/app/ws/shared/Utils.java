/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wsecu.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author mark.jones
 */
@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        }

        return new String(returnValue);
    }
}

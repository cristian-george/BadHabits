package com.example.badhabits.helper;

import com.example.badhabits.models.UserModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidators {

    public static boolean isPasswordValid(String password, String repeatPassword) {
        return password.equals(repeatPassword) && password.length() >= 4;
    }

    public static boolean isUsernameValid(String username) {
        return username.length() >= 4;
    }

    public static boolean isEmailValid(String email, ArrayList<UserModel> users) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        boolean isUsed = false;
        for (UserModel user : users) {
            if (user.getEmail().equals(email)) {
                isUsed = true;
                break;
            }
        }

        return matcher.matches() && !isUsed;
    }
}

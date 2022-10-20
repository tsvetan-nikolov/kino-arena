package com.kinoarena.kinoarena.util;

public class Validator {
    private static final String PASSWORD_PATTERN = "\\A(?=\\S*[0-9])(?=\\S*[a-z])(?=\\S*[A-Z])\\S{8,}\\z";
    private static final String PASSWORD_LOWERCASE = ".*[a-z].*";
    private static final String PASSWORD_UPPERCASE = ".*[A-Z].*";
    private static final String PASSWORD_DIGITS = ".*[0-9].*";

    public static boolean validatePassword(String password) {
        if(password == "" || password == null) {
            System.out.println("Please enter new password");
            return false;
        }

        if(password.matches(PASSWORD_PATTERN)) {
            return true;
        }

        if(password.length() < 8) {
            System.out.println("Password should be at least 8 characters");
        }

        containsLowercase(password);
        containsUppercase(password);
        containsDigits(password);

        return false;
    }

    private static void containsDigits(String password) {
        if(!password.matches(PASSWORD_DIGITS)) {
            System.out.println("Password has no digits");
        }
    }

    private static void containsUppercase(String password) {
        if(!password.matches(PASSWORD_UPPERCASE)) {
            System.out.println("Password has no uppercase letters");
        }
    }

    private static void containsLowercase(String password) {
        if(!password.matches(PASSWORD_LOWERCASE)) {
            System.out.println("Password has no lowercase letters");
        }
    }


}

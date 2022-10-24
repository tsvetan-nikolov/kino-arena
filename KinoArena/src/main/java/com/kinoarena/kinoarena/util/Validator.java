package com.kinoarena.kinoarena.util;

import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;

import java.time.LocalDate;

import static com.kinoarena.kinoarena.util.Constants.*;

public class Validator {

    public static boolean validatePassword(String password) {
        //TODO with spring security and constants
        if (password.equals("")) {
            throw new UnauthorizedException("Please enter new password");
        } else if (password.length() < 8) {
            throw new UnauthorizedException("Password should be at least 8 characters");
        }

        if (password.matches(Constants.PASSWORD_PATTERN)) {
            return true;
        }

        containsLowercase(password);
        containsUppercase(password);
        containsDigits(password);

        return false;
    }

    private static void containsDigits(String password) {
        if (!password.matches(PASSWORD_DIGITS)) {
            throw new UnauthorizedException("Password has no digits");
        }
    }

    private static void containsUppercase(String password) {
        if (!password.matches(PASSWORD_UPPERCASE)) {
            throw new UnauthorizedException("Password has no uppercase letters");
        }
    }

    private static void containsLowercase(String password) {
        if (!password.matches(PASSWORD_LOWERCASE)) {
            throw new UnauthorizedException("Password has no lowercase letters");
        }
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if(!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            return false;
        }

        return true;
    }

    public static boolean dateIsValid(LocalDate dateOfBirth) {
        if(dateOfBirth.isAfter(LocalDate.now())) {
            return false;
        }

        return true;
    }
}


package com.kinoarena.kinoarena.util;

public class Constants {

    public static final String PASSWORD_PATTERN = "\\A(?=\\S*[0-9])(?=\\S*[a-z])(?=\\S*[A-Z])\\S{8,}\\z";

    public static final String PASSWORD_LOWERCASE = ".*[a-z].*";

    public static final String PASSWORD_UPPERCASE = ".*[A-Z].*";

    public static final String PASSWORD_DIGITS = ".*[0-9].*";

    public static final String ROLE_ADMIN = "Admin";

    public static final String ROLE_USER = "USER";
}

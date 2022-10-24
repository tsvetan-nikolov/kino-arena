package com.kinoarena.kinoarena.constant;

public class AuthConstants {

    public static class JWT {
        public static final long EXPIRATION_TIME = 864_000_000; // 10 days

        public static final String HEADER_AUTHORIZATION = "Authorization";

        public static final String TOKEN_PREFIX = "Bearer ";
    }

    public static class Role {

        public static final String ROLE_USER = "ROLE_USER";

        public static final String ROLE_ADMIN = "ROLE_ADMIN";

    }

    public static class Password {
        public static final String PASSWORD_PATTERN = "\\A(?=\\S*[0-9])(?=\\S*[a-z])(?=\\S*[A-Z])\\S{8,}\\z";

        public static final String PASSWORD_LOWERCASE = ".*[a-z].*";

        public static final String PASSWORD_UPPERCASE = ".*[A-Z].*";

        public static final String PASSWORD_DIGITS = ".*[0-9].*";
    }

    private AuthConstants() {
    }
}

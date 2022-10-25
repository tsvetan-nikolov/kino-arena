package com.kinoarena.kinoarena.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoarena.kinoarena.config.filter.JWTAuthenticationFilter;
import com.kinoarena.kinoarena.config.filter.JWTAuthorizationFilter;
import com.kinoarena.kinoarena.services.JwtService;
import com.kinoarena.kinoarena.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
public class JWTFilterConfigurer extends AbstractHttpConfigurer<JWTFilterConfigurer, HttpSecurity> {
    private final UserService userDetailsService;

    private final String secretKey;

    private final ObjectMapper objectMapper;

    private final JwtService jwtService;

    public JWTFilterConfigurer(
            UserService userDetailsService,
            @Value("${jwt.secret.key}") String secretKey,
            ObjectMapper objectMapper,
            JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new JWTAuthenticationFilter(
                        objectMapper, authenticationManager, secretKey, userDetailsService, jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, secretKey, userDetailsService));

    }
}
package com.kinoarena.kinoarena.services;


import com.auth0.jwt.JWT;
import com.kinoarena.kinoarena.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kinoarena.kinoarena.constant.AuthConstants.JWT.EXPIRATION_TIME;

@Service
public class JwtService {

    public String generateToken(User user, String secretKey) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                .withClaim("userId", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secretKey.getBytes()));
    }
}

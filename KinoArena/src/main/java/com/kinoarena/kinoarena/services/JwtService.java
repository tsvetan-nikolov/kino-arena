package com.kinoarena.kinoarena.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kinoarena.kinoarena.model.entities.InvalidToken;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.repositories.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kinoarena.kinoarena.util.constant.AuthConstants.JWT.EXPIRATION_TIME;
import static com.kinoarena.kinoarena.util.constant.AuthConstants.JWT.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class JwtService { /*TODO cronjob to delete expired tokens*/
    private final InvalidTokenRepository invalidTokenRepository;

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

    public String validateToken(String token, String secretKey) {
        token = token.replace(TOKEN_PREFIX, "");
        if (invalidTokenRepository.findByToken(token).isPresent()) return null;

        return JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    public void invalidateToken(String token) {
        token = token.replace(TOKEN_PREFIX, "");

        if (invalidTokenRepository.findByToken(token).isPresent()) return;

        Date expiresAt = JWT.decode(token).getExpiresAt();
        InvalidToken invalidToken = InvalidToken
                .builder()
                .token(token.replace(TOKEN_PREFIX, ""))
                .expiration(convertToLocalDateTime(expiresAt))
                .build();

        invalidTokenRepository.save(invalidToken);
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void deleteExpiredInvalidTokens() {
        invalidTokenRepository.deleteAllByExpirationBefore(LocalDateTime.now());
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}

package com.kinoarena.kinoarena.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoarena.kinoarena.model.DTOs.user.request.LoginRequestDTO;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kinoarena.kinoarena.util.constant.AuthConstants.JWT.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    private final AuthenticationManager authenticationManager;

    private final String secretKey;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public JWTAuthenticationFilter(
            ObjectMapper objectMapper,
            AuthenticationManager authenticationManager,
            String secretKey,
            UserDetailsService userDetailsService,
            JwtService jwtService) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            LoginRequestDTO loginDTO =
                    objectMapper.readValue(req.getInputStream(), LoginRequestDTO.class);

            UserDetails user = userDetailsService.loadUserByUsername(loginDTO.getEmail());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user, loginDTO.getPassword(), user.getAuthorities()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException {
        User userEntity = ((User) auth.getPrincipal());

        String token = jwtService.generateToken(userEntity, secretKey);

        res.setContentType(APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("UTF-8");
        res.addHeader(AUTHORIZATION, TOKEN_PREFIX + token);
        res.getWriter().write("Log in was successful!");
//        res.getWriter().write(objectMapper.writeValueAsString(Map.of("access_token", token)));
    }
}

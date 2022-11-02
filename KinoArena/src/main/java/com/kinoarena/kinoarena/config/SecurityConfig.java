package com.kinoarena.kinoarena.config;


import com.kinoarena.kinoarena.model.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import java.util.function.Function;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig {

    private final JWTFilterConfigurer jwtFilterConfigurer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/cinemas")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/cinemas/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/brands")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/brands/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/movies")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/movies/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .cors()
                .disable()
                .httpBasic()
                .disable()
                .formLogin()
                .disable()
                .logout()
                .disable()
                .apply(jwtFilterConfigurer)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        var source = new UrlBasedCorsConfigurationSource();
//        var corsConfig = new CorsConfiguration().applyPermitDefaultValues();
//        corsConfig.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return source;
//    }

    @Bean
    public Function<User, Integer> extractUserId() {
        return (User::getId);
    }
}

package com.example.jwt_test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value("${jwt.secretKey}") private String secretKey;

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                // 특정 경로에 대한 요청을 허용하겠다
                .requestMatchers("/members/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/members").permitAll()
                .requestMatchers(HttpMethod.POST, "/members").permitAll()
                // ADMIN이 아니라면 허용하지 않아도 된다.
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                // (내가 만든 필터, 특정 필터)
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}

package com.example.ex01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Value("${jwt.secretKey}") private String secretKey;

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                // 이전에 WebConfig에서 작성한 것 중 한 줄 빼고 WebSecurityConfig에 모아넣는다.
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*")); //모든 ip 허용.허용할 FrontEnd ip설정
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // 허용할 HTTP 메서드
                    config.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
                    config.setAllowCredentials(true); // 쿠키 및 인증 정보 허용
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                // 특정 경로에 대한 요청을 허용하겠다
                .requestMatchers("/mem/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/mem").permitAll()
                .requestMatchers(HttpMethod.GET, "/mem/{fileName}/image").permitAll()
                .requestMatchers(HttpMethod.POST, "/mem").permitAll()
                // ADMIN이 아니라면 허용하지 않아도 된다.
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                // (내가 만든 필터, 특정 필터)
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}

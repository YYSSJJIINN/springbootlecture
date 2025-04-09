package com.example.ex01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // Ctrl+O눌러서 addCors찾기
    public void addCorsMappings(CorsRegistry registry) {
        // "/**" 는 컨트롤러에 대한 모든 경로를 허용하겠다는 뜻이다.
        registry.addMapping("/**")      // 컨트롤러 경로 허용
                // 프론트에 대한 모든 경로를 허용하겠다는 뜻이다.
                .allowedOriginPatterns("*")     // front 주소 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                // 쿠키전달도 허용하겠다는 뜻이다.
                .allowCredentials(true);    // 쿠키 전달 허용
    }
}

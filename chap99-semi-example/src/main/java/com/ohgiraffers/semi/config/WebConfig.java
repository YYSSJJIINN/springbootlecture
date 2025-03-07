package com.ohgiraffers.semi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 아래 설정 정보는 @Configuration 어노테이션이 정의된 클래스라면 어디든 정의해도 무방하다.
 * 다만, 해당 샘플 프로젝트는 @SpringBootApplication 외 @Configuration이 정의된 클래스는 SecurityConfig 뿐이다.
 * SecurityConfig는 Spring Security 관련 Bean 설정들만 모아놓기 위해 해당 클래스를 별도로 준비했다.
 * */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${image.add-resource-handler}")
    private String ADD_RESOURCE_HANDLER;

    @Value("${image.add-resource-locations}")
    private String ADD_RESOURCE_LOCATION;

	/* 이미지 파일, CSS 및 JS 파일 등 정적 리소스 핸들러 설정 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 핸들러 설정 (CSS, JS 등)
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        
        // 이미지 리소스 핸들러 설정
        registry.addResourceHandler(ADD_RESOURCE_HANDLER)
                .addResourceLocations(ADD_RESOURCE_LOCATION);
    }
}

package com.ohgiraffers.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* 인터셉터를 정의하는 것만으로는 동작시킬 수 없으며 등록을 해야한다.
    * WebMvcConfigurer는 Spring MVC에서 다양한 설정을 지원하는 인터페이스다.
    * 주로 인터셉터, 리소스 핸들러, CORS 설정 등을 추가할 때 사용한다.
    * */

    /* 생성자 주입을 지향하지만, 여기서는 인터셉터에 집중하기 위해 간단하게 필드 주입 방식을 사용한다. */
    @Autowired
    private StopwatchInterceptor stopwatchInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new StopwatchInterceptor())
                .addPathPatterns("/stopwatch");
    }
}

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

        /* 전체 요청 중, 요청 URL의 '/stopwatch' 패턴만 인터셉터가 가로채도록 설정 */
//        registry.addInterceptor(new StopwatchInterceptor(new MenuService()))
//                .addPathPatterns("/stopwatch");

        /* 만약 모든 요청에 대해서 인터셉터를 적용하고 싶다면 아래와 같이 적용할 수 있다.
         * 정적 리소스 또는 에러 포워딩 경로 등은 excludedPathPatterns 메서드를 통해 제외해주면 된다.
         * */
        registry.addInterceptor(stopwatchInterceptor)
                /* 일단 모든 경로에 대해 인터셉터를 동작시키도록 한다. */
                .addPathPatterns("/*")
                /* static 하위의 정적 리소스는 인터셉터가 적용되지 않도록 한다. */
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**")
                /* /error 로 포워딩 되는 경로도 제외해주어야 한다. */
                .excludePathPatterns("/error");
    }
}

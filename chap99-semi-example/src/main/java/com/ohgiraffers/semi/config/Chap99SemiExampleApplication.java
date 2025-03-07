package com.ohgiraffers.semi.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Spring Boot Application 메인 클래스가 현재 config 패키지에 있으므로,
 * 아래와 같이 설정해 모든 패키지를 스캔하도록 하여 컴포넌트 스캔을 활성화 시킨다.
 * */
@SpringBootApplication(scanBasePackages = "com.ohgiraffers.semi")
/* 마이바티스 매퍼 인터페이스를 스캔하여 매퍼 인터페이스를 찾아 bean으로 등록한다. */
@MapperScan(basePackages = "com.ohgiraffers.semi", annotationClass = Mapper.class)
public class Chap99SemiExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap99SemiExampleApplication.class, args);
    }

}

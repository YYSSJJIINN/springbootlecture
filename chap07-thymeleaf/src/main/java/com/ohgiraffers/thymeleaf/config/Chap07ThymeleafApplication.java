package com.ohgiraffers.thymeleaf.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 다른 패키지의 내용도 읽기 위해서 어노테이션에 세부작성을 해준다.
@SpringBootApplication(scanBasePackages = "com.ohgiraffers.thymeleaf")
public class Chap07ThymeleafApplication {

    public static void main(String[] args) {

        SpringApplication.run(Chap07ThymeleafApplication.class, args);
    }

}

package com.ohgiraffers.crud.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ohgiraffers.crud")
@MapperScan(basePackages = "com.ohgiraffers.crud", annotationClass = Mapper.class)
public class Chap08CrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(Chap08CrudApplication.class, args);
    }

}
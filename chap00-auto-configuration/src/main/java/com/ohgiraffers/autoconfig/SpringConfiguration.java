package com.ohgiraffers.autoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringConfiguration {

    // application.properties에서 작성한 값을 불러올 수 있다.
    @Value("${test.myValue}")
    public String myValue;
    @Value("${test.age}")
    public String age;
    @Value("${test.array}")
    public String[] arr;

    @Bean
    public Object customPropertyReadTest() {

        System.out.println("myValue = " + myValue);
        System.out.println("age = " + age);
//        System.out.println("arr = " + arr);
        Arrays.stream(arr).forEach(System.out::println);

        return new Object();
    }
}

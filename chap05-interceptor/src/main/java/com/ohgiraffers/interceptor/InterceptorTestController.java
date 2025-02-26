package com.ohgiraffers.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class InterceptorTestController {

    @GetMapping("stopwatch")
    public String handlerMethod() throws InterruptedException {

        System.out.println("핸들러 메서드 호출됨...");

        /* 아무것도 하는 일이 없으면 사실상 0초에 가깝게 나올 것이므로 강제로 일정 시간동안 스레드를 재운다. */
        Thread.sleep(1000);
        System.out.println("1초 경과...");
        Thread.sleep(1000);
        System.out.println("2초 경과...");
        Thread.sleep(1000);
        System.out.println("3초 경과...");

        return "result";
    }
}

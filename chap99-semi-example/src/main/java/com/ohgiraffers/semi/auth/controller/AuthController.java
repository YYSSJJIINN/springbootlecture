package com.ohgiraffers.semi.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "exception", required = false) String exception,
                       Model model) {
                           
        /* 에러와 예외를 모델에 담아 view로 전달 */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        
        return "auth/login";
    }

    /*
     * 이 곳에 원래 POST /auth/login 요청을 처리해주는 핸들러 메서드를 작성했는데,
     * */
} 
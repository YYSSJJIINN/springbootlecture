package com.example.jwt_test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public ResponseEntity getAdmin() {
        return ResponseEntity.ok().body("admin 페이지 접속");
    }

    @GetMapping("/index")
    public ResponseEntity index() {
        return ResponseEntity.ok("admin index 접속");
    }
}

package com.example.jwt_test.controller;

import com.example.jwt_test.dto.MemberDTO;
import com.example.jwt_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService ms;

    // postman으로 username과 password만 입력해도 @PrePersist 덕분에 role은 디폴트값이 입력된다.
    @PostMapping
    public ResponseEntity<String> register(@RequestBody MemberDTO dto) {
        log.info("dto : {}", dto);
        ms.register(dto);

        return ResponseEntity.ok().body("가입 성공");
    }
}

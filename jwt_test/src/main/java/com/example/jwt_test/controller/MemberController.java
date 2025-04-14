package com.example.jwt_test.controller;

import com.example.jwt_test.dto.MemberDTO;
import com.example.jwt_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService ms;

    // 회원가입
    // postman으로 username과 password만 입력해도 @PrePersist 덕분에 role은 디폴트값이 입력된다.
    @PostMapping
    public ResponseEntity<String> register(@RequestBody MemberDTO dto) {
        log.info("dto : {}", dto);
        ms.register(dto);

        return ResponseEntity.ok().body("가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String password,
                                        @RequestParam String username) {

        return ResponseEntity.ok().body(ms.login(username, password));
    }

    @GetMapping
    public ResponseEntity<List> getList() {
        return ResponseEntity.ok().body(ms.getList());
    }

    @PutMapping
    public ResponseEntity<String> modify(@RequestBody MemberDTO dto, Authentication authentication) {
        log.info("authentication : {}", authentication.getName());
        ms.modify(dto);
        return ResponseEntity.ok("수정");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity delete(@PathVariable String username) {

        ms.delete(username);

        return ResponseEntity.ok("삭제");
    }
}

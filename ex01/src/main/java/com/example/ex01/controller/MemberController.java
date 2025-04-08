package com.example.ex01.controller;

import com.example.ex01.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

// 문자열에 대한 데이터를 돌려주는 방식
@RestController
// 로그 찍는 어노테이션
@Slf4j
public class MemberController {

    @GetMapping("/")
    public void testLog() {
        log.debug("debug execute");
        log.info("info execute");
        log.warn("warn execute");
        log.error("error execute");
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        // 헤더라는 값을 수정할 수 있다.
        return ResponseEntity.ok("될까?");
    }

//    @GetMapping("/test")
//    public String test() {
//        // 헤더라는 값을 수정할 수 있다.
//        return "될까?";
//    }

    @GetMapping("/members")
    public ResponseEntity memberAll() {
        ArrayList<MemberDTO> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            MemberDTO dto = new MemberDTO("aaa" + i, "aaa" + i, "role" + i);
            list.add(dto);
        }
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity memberOne(@PathVariable String id) {
        log.debug("받은 id {}", id);
        return ResponseEntity.ok(new MemberDTO("test", "test", "role"));
    }

    @PostMapping("/members")
    // FormData일때는 리퀘스트파람 사용
    // JSON형식으로 문자열을 그냥 가져다줄때는 리퀘스트바디 사용
    public ResponseEntity memberInsert(@RequestBody MemberDTO dto) {
        log.info("dto 확인 {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장 성공");
    }
}

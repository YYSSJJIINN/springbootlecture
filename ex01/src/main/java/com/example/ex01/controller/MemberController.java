package com.example.ex01.controller;

import com.example.ex01.dto.MemberDTO;
import com.example.ex01.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

// 문자열에 대한 데이터를 돌려주는 방식
@RestController
// 로그 찍는 어노테이션
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService ms;

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
        log.debug("dto 확인 {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장 성공");
    }

    @PutMapping("/members/{id}")
    public ResponseEntity memberUpdate(@PathVariable String id,
                                        @RequestBody MemberDTO dto) {

        log.info("modify id : {}", id);
        log.info("modify dto : {}", dto);
        // return으로 돌려줄 게 없을 때 사용하는 것이 build다.
//        return ResponseEntity.noContent().build();
        // 성공적으로 진행은 됐지만 돌아오는 게 없는 204번 코드가 NO_CONTENT
        // ok에는 성공시 true가, 실패시 false가 들어온다.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity memberDelete(@PathVariable String id) {
        log.debug("삭제 : {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 데이터 추가
    @PostMapping("/mem")
    public ResponseEntity insert(@RequestBody MemberDTO dto) {
        log.debug("insert {}", dto);
        int result = ms.insert(dto);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하는 id 임");
    }

    // 전체 데이터 조회
    @GetMapping("/mem")
    public ResponseEntity getList(@RequestParam(defaultValue = "0") int start) {
//        try {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            for( int i = 0; i < 5; i++) {
//                log.debug("{}.back", i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        return ResponseEntity.status(HttpStatus.OK).body(ms.getList(start));
    }

    // 데이터 수정
    @PutMapping("/mem/{id}")
    public ResponseEntity update(@PathVariable("id") String id,
                                    @RequestBody MemberDTO dto) {
        log.debug("modify {}", dto);
        log.debug("modify {}", id);
        int result = ms.update(dto, id);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
        else if(result == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 id가 존재하지 않습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 항목이 없습니다.");
    }

    // 데이터 삭제
    @DeleteMapping("/mem/{id}")
    public ResponseEntity mDelete(@PathVariable String id) {
        log.debug("delete {}", id);
        int result = ms.mDelete(id);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 id가 존재하지 않습니다.");
    }

    @PostMapping("/mem/login1")
    public ResponseEntity login1(@RequestBody Map<String, String> map) {
        log.debug("login map : {}", map);
        int result = ms.login(map.get("username"), map.get("password"));
        if( result == 0 )
            return ResponseEntity.status(HttpStatus.OK).body("로그인이 성공했습니다.");
        else if( result == 1 )
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("일치하지 않습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 id 입니다.");
    }

    @PostMapping("/mem/login")
    public ResponseEntity login(@ModelAttribute MemberDTO dto) {
        log.debug("login dto : {}", dto);
        int result = ms.login(dto.getUsername(), dto.getPassword());
        if( result == 0 )
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        else if( result == 1)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비번틀림");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id없음");
    }

    // 개인정보 상세페이지
    @GetMapping("/mem/{id}")
    public ResponseEntity memOne(@PathVariable("id") String username) {
        log.debug("받은 id {}", username);
        MemberDTO dto = ms.getOne(username);
        if(dto != null)
            // 이렇게 작성하면 상세페이지는 더미의 것이 보임
//            return ResponseEntity.ok.body("성공");
            // 이렇게 작성해야 상세페이지가 DB의 것으로 보임
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보를 찾지 못하였습니다.");
    }
}
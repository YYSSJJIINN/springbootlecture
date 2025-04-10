package com.example.db_test.controller;

import com.example.db_test.dto.MemberDTO;
import com.example.db_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    // DAO나 Mapper 사용할 때도,
    // 원래 Controller -> Service -> Mapper였음
    private final MemberService ms;

    // 데이터 저장하기
    @PostMapping("members")
    public ResponseEntity insert(@RequestBody MemberDTO dto) {
        log.info("ctrl dto : {}", dto);
        int result = ms.insert(dto);
        if( result == 0 )
//            return ResponseEntity.status(HttpStatus.CREATED).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // 데이터 불러오기
    @GetMapping("members")
    public ResponseEntity list() {
        List<MemberDTO> list = ms.getList();
        return ResponseEntity.ok().body(list);
    }

    // 상세정보 불러오기
    @GetMapping("/members/{number}")
    public ResponseEntity getData(@PathVariable("number") long number) {

        MemberDTO dto = ms.getData(number);
        if( dto != null )
            // 원래 괄호안에 "성공"이라고 적혀있었는데
            // 그러면 postman에서 get요청 날리면 성공만 보임
            // 데이터 값을 보고 싶다면 dto로 바꾼다.
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 데이터 삭제하기
    @DeleteMapping("members/{num}")
    public ResponseEntity deleteData(@PathVariable("num") long num) {

        int result = ms.deleteData(num);
        if( result == 1 )
//            return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.OK).body("삭제성공");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 데이터 수정
    @PutMapping("/member/{id}")
    public ResponseEntity update(@PathVariable("id") String userId,
                                    @RequestBody MemberDTO dto) {
        int result = ms.updateData(userId, dto);
        if( result == 1 )
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 한 페이지에 몇 개를 보여줄 것인지 페이징처리
    @GetMapping("list")
    // 입력되는 값이 없으면 기본으로 시작할 페이지 번호
    public ResponseEntity list_2(@RequestParam(defaultValue = "0") int start,
                                @RequestParam(defaultValue = "3") int page) {
//        log.info("ctrl start : {}", start);
        List<MemberDTO> list = ms.getListPage(start, page);
        return ResponseEntity.ok().body(list);
        // localhost:8080/list?start=0&page=3
        // localhost:8080/list?start=1&page=3
        // localhost:8080/list?start=2&page=3
        // localhost:8080/list?start=0&page=5
        // localhost:8080/list?start=1&page=5
    }

    // 상세정보 불러오기
    @GetMapping("/api/content/{number}")
    public ResponseEntity getContent(@PathVariable("number") long number) {

        MemberDTO dto = ms.getContent(number);
        if( dto != null )
            // 원래 괄호안에 "성공"이라고 적혀있었는데
            // 그러면 postman에서 get요청 날리면 성공만 보임
            // 데이터 값을 보고 싶다면 dto로 바꾼다.
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("api/content")
    public ResponseEntity insertContent(@RequestBody MemberDTO dto) {
        log.info("ctrl dto : {}", dto);
        int result = ms.insertContent(dto);
        // 성공이면 1로 처리
        if( result == 1 )
            return ResponseEntity.status(HttpStatus.CREATED).build();
//            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

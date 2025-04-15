package com.example.ex01.service;

import com.example.ex01.domain.MemberEntity;
import com.example.ex01.dto.MemberDTO;
import com.example.ex01.repo.MemberDataSet;
import com.example.ex01.repo.MemberRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.ex01.utils.JwtUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    MemberDataSet ds;

    private final HttpSession session;

    private final MemberRepo repo;

    final String DIR = "uploads/";  // 뒤에는 이미지 이름이 들어간다.

    public int insert(MemberDTO dto, MultipartFile file) {
        int result = 0;
        // 더미데이터
//        result = ds.insert(dto);
        try {
            String fileName = null;
            // 만약 해당 파일이 전송되어있지 않다면(= 비어 있다면)
            if(file.isEmpty()) {
                fileName = "nan";
            } else {
                fileName = file.getOriginalFilename();
            }
            repo.save(new MemberEntity(dto));
            result = 1;

            Path path = Paths.get(DIR + fileName);
            Files.createDirectories(path.getParent());
        } catch (Exception e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return result;
    }

//    // 더미 버전
//    public ArrayList<MemberDTO> getList() {
//        return ds.getList();
//    }

//    // DB버전
//    public List<MemberDTO> getList() {
//        return repo.findAll().stream()
//                .map(entity -> new MemberDTO(entity))
//                .toList();
//    }

    // DB버전
    // 페이징 추가
    public Map<String, Object> getList(int start) {

        // start가 0보다 크다면 -1을 처리해서 넣어라
        // cuz 스타트가 0부터 시작하니까~
        // 그래야 ?start=2를 했을 때 2페이지
        // 이거 없으면 ?start=1이 2페이지
        start = start > 0? start -1 : start;

        int size = 3;   // 한 페이지에 3개 항목
        Pageable pageable = PageRequest.of(start, size, Sort.by(Sort.Order.desc("id")));
        Page<MemberEntity> page = repo.findAll(pageable);
        List<MemberEntity> listE = page.getContent();

        Map<String, Object> map = new HashMap<>();
        map.put("list", listE.stream().map(entity -> new MemberDTO(entity)).toList());
        map.put("totalPages", page.getTotalPages());
        map.put("currentPage", page.getNumber() + 1);

        return map;
    }

    public int update(MemberDTO dto, String id) {
        if(dto.getUsername() == null || dto.getPassword() == null || dto.getRole() == null)
            return -1;
//        // 더미버전
//        return ds.update(dto, id);
        MemberEntity entity = repo.findByUsername(dto.getUsername());
        if(entity != null) {
            // 수정 가능한 항목만 입력한다.
            entity.setPassword(dto.getPassword());
            entity.setRole(dto.getRole());
            repo.save(entity);
            return 1;
        }
        return 0;
    }

    public int mDelete(String id) {
//        int result = 0;
//        result = ds.delete(id);
//        return ds.getList().remove(id) ? 1 : 0;

//        // 더미버전
//        return ds.mDelete(id);

        // DB버전
        MemberEntity entity = repo.findByUsername(id);
        if(entity != null) {
            repo.delete(entity);
            return 1;
        }
        return 0;
    }

//    public int login( String username, String password ){
////        // 더미버전
////        int result = 0;
////        result = ds.login(username, password);
////        if( result == 0 ){
//        // DB버전
//        int result = -1;
//        MemberEntity entity = repo.findByUsername(username);
//        if( entity != null ){
//            // 비밀번호 틀림
//            result = 1;
//            if(entity.getPassword().equals(password)) {
//                session.setAttribute("username", username);
//                // 로그인성공
//                result = 0;
//            }
//        }
//        return result;
//    }

public Map<String, Object> login( String username, String password ){
    // DB버전
    int result = -1;
    Map<String, Object> map = new HashMap<>();
    MemberEntity entity = repo.findByUsername(username);
    if( entity != null ){
        // 비밀번호 틀림
        result = 1;
        if(entity.getPassword().equals(password)) {
            // 로그인성공
            result = 0;
            map.put("token", JwtUtil.createJwt(username, secretKey, entity.getRole()));
        }
    }
    map.put("result", result);
    return map;
}

    // 상세정보 불러오기
    public MemberDTO getOne(String username) {
//        // 더미버전
//        MemberDTO dto = null;
//        dto = ds.getOne(username);
//        return dto;
        return new MemberDTO(repo.findByUsername(username));
    }
}

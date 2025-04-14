package com.example.ex01.service;

import com.example.ex01.domain.MemberEntity;
import com.example.ex01.dto.MemberDTO;
import com.example.ex01.repo.MemberDataSet;
import com.example.ex01.repo.MemberRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    MemberDataSet ds;

    private final HttpSession session;

    private final MemberRepo repo;

    public int insert(MemberDTO dto) {
        int result = 0;
        // 더미데이터
//        result = ds.insert(dto);
        try {
            repo.save(new MemberEntity(dto));
            result = 1;
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

    // DB버전
    public List<MemberDTO> getList() {
        return repo.findAll().stream()
                .map(entity -> new MemberDTO(entity))
                .toList();
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

    public int login( String username, String password ){
//        // 더미버전
//        int result = 0;
//        result = ds.login(username, password);
//        if( result == 0 ){
        // DB버전
        int result = -1;
        MemberEntity entity = repo.findByUsername(username);
        if( entity != null ){
            // 비밀번호 틀림
            result = 1;
            if(entity.getPassword().equals(password)) {
                session.setAttribute("username", username);
                // 로그인성공
                result = 0;
            }
        }
        return result;
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

package com.example.jwt_test.service;

import com.example.jwt_test.domain.MemberEntity;
import com.example.jwt_test.dto.MemberDTO;
import com.example.jwt_test.repository.MemberRepo;
import com.example.jwt_test.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final MemberRepo repo;

    public void register(MemberDTO dto) {
        repo.save(new MemberEntity(dto));
    }

    public String login(String username, String password) {
        // 기본 값을 실패로
        String result = "실패";

        MemberEntity entity = repo.findByUsername(username);

        // MemberEntity에서 username을 찾아 그 entity가 null 값이 아니라면 성공
        if(entity != null) {
            if(entity.getPassword().equals(password)) {
//                result = "성공";
                // 로그인 성공을 한다면 사용자에게 토큰값을 리턴
                result = JwtUtil.createJwt(username, secretKey, entity.getRole());
            }
        }

        // 최종 결과 값을 반환한다.
        return result;
    }

    public List<MemberDTO> getList() {
        return repo.findAll().stream().map(mem -> new MemberDTO(mem)).toList();
    }

    public void modify(MemberDTO dto) {

        MemberEntity entity = repo.findByUsername(dto.getUsername());

        if(entity != null) {
            entity.setRole(dto.getRole());
            repo.save(entity);
        }
    }

    public void delete(String username) {

        MemberEntity entity = repo.findByUsername(username);

        if(entity != null) {
            repo.delete(entity);
        }
    }
}

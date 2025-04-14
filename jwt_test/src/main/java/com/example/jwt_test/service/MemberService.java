package com.example.jwt_test.service;

import com.example.jwt_test.domain.MemberEntity;
import com.example.jwt_test.dto.MemberDTO;
import com.example.jwt_test.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepo repo;

    public void register(MemberDTO dto) {
        repo.save(new MemberEntity(dto));
    }
}

package com.ohgiraffers.semi.member.model.service;

import com.ohgiraffers.semi.member.model.dao.MemberMapper;
import com.ohgiraffers.semi.member.model.dto.MemberDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(MemberDTO member) {
        
        // 비밀번호 암호화
        member.setMemberPassword(passwordEncoder.encode(member.getPassword()));
        
        // 회원 정보 저장
        memberMapper.insertMember(member);
        
        // 회원 권한 저장 (기본 권한: ROLE_USER)
        memberMapper.insertMemberRole(member.getMemberCode());
    }

    public MemberDTO findMember(String memberId) {
        return memberMapper.findByMemberId(memberId);
    }

    @Transactional
    public void update(MemberDTO member) {
        // 회원 정보 업데이트
        memberMapper.updateMember(member);
    }
} 
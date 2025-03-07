package com.ohgiraffers.semi.member.model.dao;

import com.ohgiraffers.semi.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    
    MemberDTO findByMemberId(String memberId);
    
    int insertMember(MemberDTO member);
    
    int insertMemberRole(int memberCode);
    
    int updateMember(MemberDTO member);
    
    int updateMemberPassword(MemberDTO member);
} 
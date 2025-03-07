package com.ohgiraffers.semi.auth.model.dao;

import com.ohgiraffers.semi.auth.model.dto.AuthorityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
    
    List<AuthorityDTO> findAuthorityByMemberCode(int memberCode);
} 
package com.example.jwt_test.repository;

import com.example.jwt_test.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// 첫 번째 들어가는 것은 엔티티 명이고, 두번째는 PK의 자료형
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
}

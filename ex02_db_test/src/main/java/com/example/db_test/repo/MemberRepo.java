package com.example.db_test.repo;

import com.example.db_test.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

// <>에 작성된 것의 틀 형식으로 Jpa를 실행해달라는 뜻
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    // findBy+보유 Entity의 변수명
    MemberEntity findByUserId(String userId);

    // 직접적인 쿼리문 작성
    @Query(value="select * from member_test where number=:n", nativeQuery = true)
    // 서비스에서 전달해주는 변수 num을 n이라고 사용하겠다
    MemberEntity findByContent(@Param("n") long num);

    @Modifying  // 해당 내용은 수정하는 내용이라는 뜻
    @Transactional
    @Query(value="insert into member_test(user_id, user_name, age) values(:id, :name, :age)", nativeQuery = true)
    int insertContent(@Param("id")String userId, @Param("name")String userName, @Param("age")int age);
}

// MemberRepo 형식의 변수 repo를 만들게 될것이고
// repo.메소드()이렇게 사용하게될것이다.
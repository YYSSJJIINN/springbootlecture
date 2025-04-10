package com.example.db_test.domain;

import com.example.db_test.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "member_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @NotNull
    @Column( unique = true, length = 20 )
    private String userId;  // user_id

    @Column( name = "user_name", nullable = false, length = 30 )
    private String userName;

    @Column( name = "age" )
    private int age;

    // dto값을 받아서 자기 자신한테 생성자하는 ??을 만들것이다.

    public MemberEntity(MemberDTO dto) {
        this.userId = dto.getUserId();
        this.userName = dto.getUserName();
        this.age = dto.getAge();
    }
}

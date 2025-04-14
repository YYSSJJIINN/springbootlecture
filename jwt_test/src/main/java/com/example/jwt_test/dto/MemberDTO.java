package com.example.jwt_test.dto;

import com.example.jwt_test.domain.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString

public class MemberDTO {
    private Long id;
    private String username;
    private String password;
    private String role;

    public MemberDTO(MemberEntity entity){

        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.role = entity.getRole();
    }
}
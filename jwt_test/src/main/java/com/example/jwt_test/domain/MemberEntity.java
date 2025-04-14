package com.example.jwt_test.domain;

import com.example.jwt_test.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member_test")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private String role;

    @PrePersist
    public void prePersist(){
        if(this.role == null)
            this.role = "USER";
    }

    public MemberEntity(MemberDTO dto){
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }
}









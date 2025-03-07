package com.ohgiraffers.semi.member.model.dto;

import com.ohgiraffers.semi.auth.model.dto.MemberRoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberDTO implements UserDetails, java.io.Serializable {
    
    private int memberCode;             // 회원식별코드
    private String memberId;            // 아이디
    private String memberPassword;      // 비밀번호
    private String memberName;          // 회원이름
    private String memberEmail;         // 이메일
	
    private List<MemberRoleDTO> memberRole;    // 회원별권한
    private Collection<GrantedAuthority> authorities;    // 권한 목록
    
    public MemberDTO() {}
    
    public MemberDTO(int memberCode, String memberId, String memberPassword, String memberName, 
                    String memberEmail, List<MemberRoleDTO> memberRole) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberRole = memberRole;
    }
    
    public int getMemberCode() {
        return memberCode;
    }
    
    public void setMemberCode(int memberCode) {
        this.memberCode = memberCode;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }
    
    public String getMemberName() {
        return memberName;
    }
    
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    public String getMemberEmail() {
        return memberEmail;
    }
    
    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
    
    public List<MemberRoleDTO> getMemberRole() {
        return memberRole;
    }
    
    public void setMemberRole(List<MemberRoleDTO> memberRole) {
        this.memberRole = memberRole;
    }
    
    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    
    @Override
    public String getPassword() {
        return this.memberPassword;
    }
    
    @Override
    public String getUsername() {
        return this.memberId;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public String toString() {
        return "MemberDTO{" +
                "memberCode=" + memberCode +
                ", memberId='" + memberId + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", memberRole=" + memberRole +
                '}';
    }
}

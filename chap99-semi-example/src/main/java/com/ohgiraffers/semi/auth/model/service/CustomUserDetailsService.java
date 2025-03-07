package com.ohgiraffers.semi.auth.model.service;

import com.ohgiraffers.semi.auth.model.dao.AuthMapper;
import com.ohgiraffers.semi.auth.model.dto.AuthorityDTO;
import com.ohgiraffers.semi.member.model.dao.MemberMapper;
import com.ohgiraffers.semi.member.model.dto.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final AuthMapper authMapper;

    public CustomUserDetailsService(MemberMapper memberMapper, AuthMapper authMapper) {
        this.memberMapper = memberMapper;
        this.authMapper = authMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        
		/* 사용자 정보 조회 */
        MemberDTO member = memberMapper.findByMemberId(memberId);

		/* 사용자 정보가 없는 경우 예외 발생 */
        if(member == null) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }

		/* 권한 조회 */
        List<AuthorityDTO> authorities = authMapper.findAuthorityByMemberCode(member.getMemberCode());

		/* Spring Security가 이해할 수 있는 권한 데이터로 변환 */
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(AuthorityDTO authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
        }
        
		/* 권한 정보 설정 */
        member.setAuthorities(grantedAuthorities);

		/* 사용자 정보 반환 */
        return member;
    }
} 
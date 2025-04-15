package com.example.ex01.config;

import com.example.ex01.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // 생성자를 통해 해당하는 값 자동주입
    private final String secretKey;

    @Override
    // 사용자가 웹 서버에 접속하면 무조건 이것을 통과할 것이다.
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("filter 실행?");

        final String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("auth : {}", auth);

        // 헤더 값이 없다면 다음 필터로 연결하고 종료해라.
        if(auth == null) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = auth.split(" ")[1];
        // JwtUtil에게 메서드 만들어준다.
        if(JwtUtil.isExpried(token, secretKey)) {
            // 해당하는 날짜가 지났다면 그냥 다음 필터로 이동한다.
            filterChain.doFilter(request, response);
            return;
        }

        // 괄호 안에 token, secretKey라고 작성해줘야 log로 authentication : ccc(username)만 찍힌다
        String username = JwtUtil.getUsername(token, secretKey);

        // 이걸 작성함으로써 로그에 'authentication : ccc, ADMIN' username외에 role도 찍힌다.
        String role = JwtUtil.getRole(token, secretKey);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                        username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 다음 필터로 넘겨주는 역할. 여기까지 끝낸다면 WebSecurity로 넘어간다.
        filterChain.doFilter(request, response);
    }
}

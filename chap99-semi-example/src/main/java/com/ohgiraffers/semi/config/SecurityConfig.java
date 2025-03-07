package com.ohgiraffers.semi.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/* 비밀번호 암호화를 위한 빈 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                // resources의 static부분이라고 생각하면 된다.
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/css/**", "/image/**");
    }

	/* Spring Security Filter Chain 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* SecurityFilterChain을 빌드하여 그대로 반환하는 형태.
         * HttpSecurity 타입의 http 인스턴스에 연속적으로 메서드 체이닝을 적용할 수 있다.
         * */
        return http
                .authorizeHttpRequests(auth -> {
                    /* 인증 요구 사항 설정 */
                    auth
                            .requestMatchers("/", "/main", "/auth/login", "/member/signup").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/product/**").permitAll()
                            /* 아래와 같이 기존에 사용했던 hasAuthority 메서드 대신, hasRole 메서드를 사용할 수도 있다.
                             * 단, 이 때 데이터베이스에 저장할 권한 이름은 ROLE_ 접두사를 붙여야 한다.
                             * 예를 들어, 권한을 "ADMIN"로 설정하고 싶다면, 데이터베이스에는 "ROLE_ADMIN"으로 저장해야 한다.
                             * 이는 스프링 시큐리티가 권한을 확인할 때, "ROLE_" 접두사를 자동으로 붙여서 비교하기 때문이다.
                             * (더 편한 방법을 선택하면 된다.)
                             * */
                            .requestMatchers("/product/admin/**").hasRole("ADMIN")
                            .requestMatchers("/order/**").hasRole("USER")
                             .requestMatchers("/member/mypage/**").hasAnyRole("ADMIN", "USER")
                            .anyRequest().authenticated();
                }).formLogin(login -> {
				    /* 로그인 페이지 설정 */
                    login.loginPage("/auth/login")
                            /* 아래와 같이 loginProcessingUrl을 설정하면 굳이 핸들러 메서드를 만들지 않아도
                             * Spring Securit의 UsernamePasswordAuthenticationFilter가가 해당 요청을 가로채서 로그인 처리를 위임한다.
                             * 그러면 UserDetailsService의 구현체의 loadUserByUsername 메서드가 호출되어 실질적인 로그인 로직이 수행된다.
                             * */
                            .loginProcessingUrl("/auth/login")
                            .defaultSuccessUrl("/")
                            /* 간단하게 로그인 실패 메시지만 정적으로 표시 */
                            .failureUrl("/auth/login?error=true")
                            .usernameParameter("memberId")
                            .passwordParameter("memberPassword")
                            .permitAll();
                }).logout(logout -> {
				    /* 로그아웃 설정 */
                    logout.logoutUrl("/auth/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                }).sessionManagement(session -> {
                    /* 세션 설정 */
                    session.invalidSessionUrl("/")
                            .maximumSessions(1);
                }).csrf(csrf ->
				        /* CSRF 보호 비활성화 */
                        csrf.disable()
                ).build();
				/* 필터 체인 빌드 */
    }
}

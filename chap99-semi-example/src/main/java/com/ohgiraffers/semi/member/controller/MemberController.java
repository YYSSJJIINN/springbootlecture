package com.ohgiraffers.semi.member.controller;

import com.ohgiraffers.semi.member.model.dto.MemberDTO;
import com.ohgiraffers.semi.member.model.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute MemberDTO member,
                        RedirectAttributes rttr) {

        try {
            memberService.signup(member);
            rttr.addFlashAttribute("successMessage", "회원가입이 완료되었습니다.");

            return "member/signup-complete";
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "회원가입 실패: " + e.getMessage());

            return "redirect:/member/signup";
        }
    }

    /* @AuthenticationPrincipal:
     * 현재 세션에 인증된(로그인된) 사용자의 정보를 손쉽게 주입받을 수 있게 해준다.
	 * (Thymeleaf-Extras-SpringSecurity6에서 보았던 sec:authentication 및 #authentication과 같다고 보면 됨)
     * Spring Security는 인증 완료된 사용자의 정보를 SecurityContext에 저장하는데,
     * 이 어노테이션을 통해 컨트롤러에서 직접 인증 객체에 접근할 수 있다.
     * 이를 통해 매번 Authentication 객체를 파라미터로 받아서 getPrincipal()을 호출하는 번거로움 없이
     * UserDetails의 구현체인 MemberDTO 타입으로 바로 주입받아 사용할 수 있다.
     * */
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal MemberDTO member,
                         Model model) {

        model.addAttribute("member", member);
        model.addAttribute("currentPage", "mypage");

        return "member/mypage";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO updateMember,
                        @AuthenticationPrincipal MemberDTO member,
                        RedirectAttributes rttr) {
        try {
            updateMember.setMemberCode(member.getMemberCode());

            memberService.update(updateMember);
            rttr.addFlashAttribute("successMessage", "회원정보가 수정되었습니다.");

            return "redirect:/member/mypage";
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "회원정보 수정 실패: " + e.getMessage());

            return "redirect:/member/mypage";
        }
    }
} 
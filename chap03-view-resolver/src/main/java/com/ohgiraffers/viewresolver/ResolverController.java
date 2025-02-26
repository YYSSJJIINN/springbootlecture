package com.ohgiraffers.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/*")
public class ResolverController {

    @GetMapping("string")
    public String stringReturning(Model model) {

        model.addAttribute("forwardMessage", "문자열로 뷰 이름 반환함...");

        /* 문자열로 "논리"View 이름을 반환한다는 의미는...
         * 반환 후 ThtmeleafViewResolver에게 'resources/templates/'를 prefix(접두사)로,
         * '.html'을 suffix(접미사)로 하여 resources/templates/result.html 파일("물리"적인 위치)을
         * 응답 View로 설정하라는 의미가 있다.
         * */
        return "result";
    }

    @GetMapping("string-redirect")
    public String stringRedirect() {

        return "redirect:/";
        // redirect:로 콜론까지 붙은걸 prefix상태라고 한다.
    }

    /* 일반적으로 리다이렉트가 되면 재요청이 발생되므로 첫 번째 request스코프는 소멸하게 된다.
     * 하지만 Spring에서는 RedirectAttributes 타입을 이용해 redirect 시 사용할 속성값을 저장할 수 있는 기능을 제공해준다.
     * */
    @GetMapping("string-redirect-attr")
    public String stringRedirectFlashAttribute(RedirectAttributes rAttr) {

        /* Redirct 시 flash 영역에 데이터를 담아서 redirect할 수 있다.
         * 이 데이터는 자동으로 Model 에 추가되기 때문에 request scope 에서 값을 꺼내 쓸 수 있다.
         * Session에 임시(flash)적으로 값을 담고 소멸하는 방식이기 때문에 session에 동일한 key 값이 존재하면 안된다.
         * (flash attribute는 주로 DML 작업 : POST -> Redirect -> GET 구조에서 많이 사용된다.)
         * */
        rAttr.addFlashAttribute("flashMessage1", "RedirectAttributes를 사용해 redirectgka...");

        return "redirect:/";
    }

    /* ModelAndView 객체는 Model 객체와 View 객체의 개념이 하나로 합쳐진 개념이다.
     * 핸들러 어댑터가 핸들러 메서드를 호출하고 반환받은 문자열을 ModelAndView로 만들어
     * Dispatcher Servlet에게 반환한다.
     * 이 때, 지금까지는 문자열을 반환했다면 ModelAndView를 대신 미리 만들어서 반환할 수도 있다.
     * */
    @GetMapping("modelandview")
    public ModelAndView modelAndViewReturning(ModelAndView mv) {

        // 모델에 데이터 추가 : 원래 model.addAttribute()로 작성했던 구문
        mv.addObject("forwardMessage", "ModelAndView를 이용한 모델과 뷰를 함께 반환...");
        // 뷰 이름 지정 : 원래 return "result"로 작성했던 구문
        mv.setViewName("result");

        return mv;
    }

    @GetMapping("modelandview-redirect")
    public ModelAndView modelAndViewRedirect(ModelAndView mv) {

        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("modelandview-redirect-attr")
    public ModelAndView modelAndViewRedirectFlashAttribute(ModelAndView mv, RedirectAttributes rAttr) {

        rAttr.addFlashAttribute("flashMessage2", "ModelAndView를 이용한 RedirectAttributes 리다이렉트...");
        mv.setViewName("redirect:/");

        return mv;
    }
}

package com.ohgiraffers.handlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/first")   // "/first/*"과 동일한 설정
public class FirstController {

    /* 핸들러의 메서드 반환값이 void일 경우, 즉 View의 이름을반환하지 않는다면
     * 요청 URL패턴이 곧 View의 이름이 된다.
     * ex) GET / first/regist 요청이 들어오면 그대로 /first/regist View로 응답하면 된다.
     * */
    @GetMapping("/regist")
    public void regist() {
        // String일땐 반환형이 이 줄에 적혀야 하지만, void로 핸들러 메서드가 작성됐을 땐 반환형이 없어야한다.
        // /first/regist가 regist.html에만 존재하면 된다.
    }

    @PostMapping("/regist")
    public String registMenu(Model model, WebRequest request) {

        /* WebRequest 객체의 getParameter 등의 메서드를 통해 클라이언트로부터 전달된 데이터를 가져올 수 있다. */
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        String responseMessage = name + "을(를) 신규 메뉴 목록의 " + categoryCode +
                "번 카테고리에 " + price + "원으로 성공적으로 등록했습니다!";

        System.out.println("뷰에 응답할 가공 메세지 확인 : " + responseMessage);

        /* 뷰에 출력할 데이터를 model에 추가 */
        model.addAttribute("msg", responseMessage);

        /* 요청 페이지의 경로와 반환할 페이지의 경로가 각각 다르기 때문에 view name에 페이지 경로 표시해야 함. */
        return "first/messagePrinter";
    }

}

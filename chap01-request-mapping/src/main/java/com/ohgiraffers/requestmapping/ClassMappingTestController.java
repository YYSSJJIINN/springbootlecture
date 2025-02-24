package com.ohgiraffers.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* 클래스 레벨에 @RequestMapping 어노테이션을 정의할 수 있다.
 * 클래스 레벨에 URL을 공통 부분을 이용해 설정하고나면
 * 매번 핸들러 메서드에 URL의 중복되는 내용을 작성하지 않아도 된다.
 * 이 때, 와일드 카드를 이용해서 조금 더 포괄적인 URL 패턴을 설정할 수 있다.
 * */
@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {

    /* 1. 클래스 레벨 매핑 + 메서드 레벨 매핑 */
//    @GetMapping("/order/regist")
//    @GetMapping("/regist")
    @GetMapping("regist")
    public String registOrder(Model model) {

        model.addAttribute("message", "GET 방식의 주문 등록용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }

    /* 2. 여러 개의 패턴을 하나의 핸들러 메서드에 매핑
    *  @RequestMapping의 value 속성에 중괄호를 이용하면 여러 개의 URL 패턴 값을 입력할 수 있다.
    * */
    @RequestMapping(value = {"modify", "delete"}, method = RequestMethod.POST)
    public String modifyAndDeleteOrder(Model model) {

        model.addAttribute("message", "POST 방식의 주문 수정/삭제 공통 처리용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }

    /* 3. Path variable
     * @PathVariable 어노테이션을 이용해 요청 URL path(=쿼리스트링)로부터 변수를 받아낼 수 있다.
     * path variable로 전달되는 {변수명}은 반드시 매개변수명과 동일해야 한다.
     * 만약, 동일하지 않으면 @PathVariable("이름")을 설정해줘야 한다.
     * 이는 REST형 웹 서비스를 설꼐할 때 유용하게 사용된다.
     * (IntelliJ의 Build 설정을 Gradle이 아닌 IntelliJ IDEA로 설정했을 경우, @PathVariable에 이름을 명시해주지 않으면 에러발생.)
     * */
    @GetMapping("detail/{orderNo}")     // 변수가 오도록 {orderNo}로 작성해준다.
    public String selectOrderDetail(Model model,
                                    @PathVariable int orderNo       // 빌드 시스템이 Gradle일 경우
//                                    @PathVariable("orderNo") int orderNo       // 빌드 시스템이 IntelliJ IDEA일 경우
    ) {

        System.out.println("Path variable 잘 꺼내지나? : " + orderNo);

        model.addAttribute("message", orderNo + "번 주문 상세 내용 조회용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }

    /* 4. 그 외의 다른 요청
     * @RequestMapping 어노테이션에 어떠한 URL도 지정해주지 않으면 요청 처리에 대한 핸들러 메서드가 준비되지 않았을 때,
     * 해당 메서드가 호출된다.
     * (브라우저 URL 상에 "/order/"로 시작하는 문자열 아무거나 입력해도 해당 메서드가 호출됨.)
     * */
    @RequestMapping
    public String etcRequest(Model model) {

        model.addAttribute("message", "/order/* 핸들러 공사중...");

        return "mappingResult";
    }
}

package com.ohgiraffers.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* DispatcherServlet은 웹 요청을 받는 즉시 @Controller가 정의된 컨트롤러 클래스에 요청 처리를 위임한다.
 * 그 과정은 컨트롤러 클래스의 핸들러 메서드에 선언된 다양한 @RequestMapping 설정 내용에 따른다.
 * */
@Controller
public class MethodMappingTestController {

    /* 핸들러 메서드 : 어노테이션을 활용해서 요청 방식과 경로에 따라 각각의 메서드를 정의할 수 있다. */

    /* 1. 핸들러 메서드 방식(HTTP Method) 미지정 :
     * @RequestMapping 어노테이션에 요청URL만 작성하고 method 방식을 지정하지 않음녀 GET, POST 등 모든 요청에 응답한다.
     * */
    @RequestMapping("/menu/regist")
    public String registMenu(Model model) {

        /* Model 객체는 응답할 페이지의 재료(=데이터)를 담는 객체라고 이해할 수 있다.
         * Model 객체에 addAttribute 메서드를 이용해 K-V 쌍을 추가하면 추후 View 영역에서 이를 꺼내 사용할 수 있다.
         * (예: 사용자 정보를 요청했다면 DB에서 조회해와서 Model에 담아두고 View에서 꺼내 표로 만들 수 있다)
         * (chap03-view-resolver에서 다시 다룰 예정.)
         * */
        // 없던거 추가하는 것이므로 req.setAttribute에 가깝다.
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메서드 호출됨...");

        /* 핸들러 메서드의 반환타입이 String일 때, 반환값은 templates 디렉토리에 있는 View Names(=html 파일)이 된다. */
        return "mappingResult";
    }

    /* 2. 요청 메서드 방식을 지정
     * @RequestMapping 어노테이션에:
     * - value : URL 패턴
     * - method : HTTP Method*/
    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String modifyMenu(Model model) {

        model.addAttribute("message", "GET 방식의 수정용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }

    /* 설명. 3. 요청 메소드 전용 어노테이션 (since v4.3)
     *  -------------------------------
     *  요청 메소드 |   어노테이션
     *  ------------|------------------
     *  POST        |   @PostMapping
     *  GET         |   @GetMapping
     *  PUT         |   @PutMapping
     *  DELETE      |   @DeleteMapping
     *  PATCH      |   @PatchMapping
     *  -------------------------------
     *  이 어노테이션들은 @RequestMapping 어노테이션에 method 속성을 사용하여 요청 방법을 지정하는 것과 같다.
     *  각 어노테이션은 해당하는 요청 메소드에 대해서만 처리할 수 있도록 제한하는 역할을 한다.
     * */
//    @RequestMapping(value = "/menu/delete", method = RequestMethod.GET)
    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {

        model.addAttribute("message", "GET 방식의 삭제용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }

//    @RequestMapping(value ="/menu/delete", method = RequestMethod.POST)
    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {

        model.addAttribute("message", "POST 방식의 삭제용 핸들러 메서드 호출됨...");

        return "mappingResult";
    }
}

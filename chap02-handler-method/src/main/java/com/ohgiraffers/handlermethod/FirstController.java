package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first")   // "/first/*"과 동일한 설정
/* @SessionAttributes:
 * 해당 어노테이션이 정의된 핸들러(=컨트롤러) 내 핸들러 메서들에게서
 * Model에 'myId2'라는 key값으로 담는(attribute) 값들은
 * Session에 담아야 한다는 어노테이션.*/
@SessionAttributes("myId2")
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

    /* 1.WebRequest로 요청 데이터를 파라미터로 전달받기:
     * 핸들러 메서드의 파라미터 선언부에 WebRequest 타입을 선언하면
     * 해당 메서드 호출 시 요청 페이지의 데이터를 전달인자로써 전달받을 수 있다.
     * 핸들러 메서드 매개변수로 Servlet을 배울 때 다루던 HttpServletRequest, HttpServletResponse도 사용가능하다.
     * (상위 타입인 ServletRequest, ServletResponse 또한 사용 가능하다.)
     * 다만, WebRequest는 HttpServletRequest의 요청 정보를 거의 대부분 그대로 가지고 있는 API로,
     * 우리가 기존에 알고 있던 Servlet 에 종속적인 개념이 아니다.
     * HttpServletRequest는 Servlet API의 일부이고, WebRequest는 Spring의 일부이기 때문에
     * Spring기반의 프로젝트에서 더 자주 사용된다.
     * (즉, Spring 기반의 프로젝트에서 Servlet의 Request 객체 개념이 필요하다면 WebRequest를 사용하면 된다고 이해하자.)
     * =====================================================
     * 요약하자면, Spring에서 요청 페이지로부터 사요앚의 입력 데이터를 받아오고자 request를 사용할 일이 이싿면
     * WebRequest를 사용하고, 동적 페이지에 전달할 데이터를 담는 그릇으로써 Model객체를 이용하면 된다.
     * */
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

    @GetMapping("/modify")
    public void modify() {}

    /* 목차. 2. @RequestParam로 요청 데이터를 파라미터로 전달 받기:
     *  요청 파라미터를 매핑하여 호출 시 값을 넣어주는 어노테이션으로 매개 변수 앞에 작성한다.
     *  form의 name 속성값과 매개변수의 이름이 다른 경우 @RequestParam("name")을 설정하면 된다.
     *  또한 어노테이션은 생략 가능하지만 명시적으로 작성하는 것이 의미 파악에 쉽다.
     *  --------------------------------------------------------------------------------------------------------------
     *  전달하는 form의 name속성이 일치하는 것이 없는 경우 400에러가 발생하는데 이는 required 속성의 기본 값이 true이기 때문이다.
     *  required 속성을 false로 하게 되면 해당 name값이 존재하지 않아도 null로 처리하며 에러가 발생하지 않는다.
     *  값이 넘어오지 않게 되면 빈 문자열 ""이 넘어오게 되는데, 이 때 컨트롤러 단에서 parsing 관련 에러가 발생할 수 있다.
     *  값이 넘어오지 않는 경우 defaultValue를 이용하게 되면 기본값으로 사용할 수 있다.
     * */
    @PostMapping("/modify")
    public String modifyMenu(Model model,
                             @RequestParam(required = false) String modifyName,
                             @RequestParam(defaultValue = "0") int modifyPrice) {

        String responseMessaqge = modifyName + "의 가격을 " + modifyPrice + "원으로 성공적으로 변경했습니다.";

        System.out.println("뷰에서 사용할 가공된 메세지 : " + responseMessaqge);

        model.addAttribute("msg", responseMessaqge);

        return "first/messagePrinter";
    }

    /* 파라미터가 여러 개인 경우, 일일이 파싱하지 않고 Map으로 한 번에 받아낼 수도 있다.
    *  이 때, Map의 key값은 name 어트리뷰트값이 된다.
    * */
    @PostMapping("modifyAll")
    public String modifyMenu(Model model,
                             @RequestParam Map<String, String> params) {

        String modifyMenu = params.get("modifyName2");
        int modifyPrice = Integer.parseInt(params.get("modifyPrice2"));

        String responseMessage = "메뉴명을 " + modifyMenu + "(으)로, 가격을 " + modifyPrice + "원으로 성공적으로 변경하였습니다.";
        System.out.println("View에서 사용할 가공된 메세지 : " + responseMessage);

        model.addAttribute("msg", responseMessage);

        return "first/messagePrinter";
    }

    @GetMapping("/search")
    public void search() {

    }

    /* 3. @ModelAttribute를 이용하는 방법:
     * DTO와 같은 Model을 '커맨드 객체'로 전달받는다.
     * 이 때, 커맨드 객체의 기본생성자를 호출하여 빈 인스턴스를 만들고, setter 메서드를 호출해
     * 각각의 필드를 초기화시켜준다.
     * ==============================================================================
     * 경우에 따라서 form 데이터를 가공을 거치지 않고 바로 다음 화면으로 전달해야 하는 경우가 존재하는데
     * 이 경우에 유용하게 사용할 수 있다.
     * @ModelAttribute("Model 객체에 담을 key값")을 지정하면 model.addAttribute()를 호출하지 않아도
     * 해당 key값으로 자동으로 model 객체에 커맨드 객체를 담아준다.
     * 해당 어노테이션도 @RequestParam과 마찬가지로 생략해도 되지만, 명시적으로 적어주도록 하자.
     * ==============================================================================
     * 1. form 데이터 개수만큼 @RequestParam을 일일이 명시하지 않아도 한 번에 받아낼 수 있음.
     * 2. addAttribute 메서드로 Model 객체에 담지 않아도 자동으로 담김.
     * */
    @PostMapping("/search")
    public String searchMenu(Model model,
                             @ModelAttribute("searchMenu") MenuDTO searchMenu) {
        // @RequestParam을 무한반복 하지 않아도 되니까 좋다.
        // MenuDTO의 것을 자동으로 담아주기에 model.addAttribute()를 작성하지 않아도 도 ㅣㄴ다.
        System.out.println("전달된 커맨드 객체 : " + searchMenu);

        return "first/searchResult";
    }

    @GetMapping("/login")
    public void login() {

    }

    /* 4-1. session 이용하기
     * HttpSession을 핸들러 메서드의 매개변수로 선언하면 해당 메서드 호출 시,
     * HttpSession 객체를 전달받아 사용할 수 있다.
     * */
    @PostMapping("/login1")
    public String sessionTest1(Model model,
                               HttpSession session,
                               @RequestParam String id) {

        session.setAttribute("myId", id);

        return "first/loginResult";
    }

    @GetMapping("/logout1")
    public String logoutTest1(HttpSession session) {    // 세션 객체를

        // 로그인 정보를 일반적으로 session에 저장한다. 따라서 로그아웃은 session을 만료시키는 것과 같은 맥락이다.
        session.invalidate();   // 세션 객체를 전달인자로 받아서 무효화한다.

        return "first/loginResult";
    }

    /* 4-2. @SessionAttributes를 이용해 session에 값 담기
     * 클래스 레벨에 @SessionAttributes 어노테이션을 이용해 세션에 값을 담을 key값을 설정해두면
     * Model 영역에 해당 key값으로 데이터가 추가될 때 session에 자동으로 등록해준다.
     * */
    @PostMapping("/login2")
    public String sessionTest2(Model model,     // model에 담으면 자동으로 session에 담아준다.
                               @RequestParam String id) {
        model.addAttribute("myId2", id);

        return "first/loginResult";
    }

    /* @SessionAttributes 어노테이션으로 등록된 데이터는 일반적인 HttpSession의 invalidate 메서드로 제거되지 않는다.
     * session의 상태를 관리하는 SessionStatus 객체의 setComplete 메서드를 호출해야 만료시킬 수 있다.
     * */
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {

        // 현재 컨트롤러 세션에 저장된 모든 정보를 제거한다.(개별 제거는 불가능, 통으로만 제거 가능)
        sessionStatus.setComplete();

        return "first/loginResult";
    }

    @GetMapping("/body")
    public void body() {

    }

    /* 목차 5. @RequestBody를 이용하는 방법
     *  해당 어노테이션은 HTTP Body 자체를 읽는 부분을 Model로 변환시켜 주는 어노테이션이다.
     *  출력해보면 쿼리스트링 형태의 문자열이 전송된다.
     *  JSON으로 전달하는 경우 Jackson의 컨버터로 자동 파싱하여 사용할 수 있다.
     *  주로 REST API작성 시 많이 사용되며, 일반적인 form 전송을 할 때는 거의 사용하지 않는다.
     *  ---------------------------------------------------------------------------------------
     *  추가적으로 헤더에 대한 정보도
     *  @RequestHeader 어노테이션을 이용해서 가져올 수 있다.
     *  @CookieValue를 이용해서 쿠키 정보도 쉽게 불러올 수 있다.
     * */
    @PostMapping("/body")
    public void bodyTest(@RequestBody String httpRequestBody,
                         @RequestHeader("content-type") String contentType,
                         @RequestHeader("host") String host,
                         @CookieValue(value = "JSESSIONID", required = false) String jSessionId)
            throws UnsupportedEncodingException {

        System.out.println("HTTP 요청 몸체(날것) : " + httpRequestBody);
        System.out.println("HTTP 요청 몸체(인코딩 적용) : " + URLDecoder.decode(httpRequestBody, "UTF-8"));

        System.out.println("HTTP 요청 헤더 - Content-Type : " + contentType);
        System.out.println("HTTP 요청 헤더 - Host : " + host);

        System.out.println("HTTP 요청 헤더 - Cookie(JSESSIONID) : " + jSessionId);

    }
}

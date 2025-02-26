package com.ohgiraffers.chap04exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* @ControllerAdvice:
 * Spring에서 예외 처리를 담당하는 어노테이션으로, 전역(global-scope) 예외 처리를 담당하게 된다.
 * 즉, 여러 개의 컨트롤러에서 발생하는 예외를 일괄적으로 처리할 수 있다.
 * 따라서 해당 어노테이션을 사용하면 모든 컨트롤러에서 발생하는 예외를 한 고셍서 처리할 수 있어
 * 중복되는 코드를 줄여준다.*/
@ControllerAdvice
public class GlobalExceptionHandler {

    /* 위에서 발생시킨 NPE 예외타입(빌트인)을 핸들링할 메서드 */
    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e) {

        System.out.println("Global 레벨의 NPE 처리기 동작 시작!");

        return "error/nullPointer";
    }
    /* 위에서 발생시킨 MemberRegistException 예외 타입(커스텀)을 핸들링할 메서드 */
    @ExceptionHandler(MemberRegistException.class)
    public String userCustomizedExceptionHandler(Model model, MemberRegistException e) {    // 1.여기의 e(exception)이

        System.out.println("Global 레벨의 MemberRegistException 처리기 동작 시작!");
        model.addAttribute("e", e);     // 2. 이곳의 e(exception)로 가고 그게 또 "e(exception)"로 가서
        // memberRegist.html의 th:text="${ e.message }"에 들어가는 것이다.

        return "error/memberRegist";
    }

    /* 상위 타입인 Exception 타입을 사용하면 구체적으로 작성되지 않은 기타 타입의 에러가 발생하더라도
     * 공통적으로 처리 가능하기 때문에 일반적으로 default 예외 처리 용도로 많이 사용된다.
     * */
    // 여러 에러들을 다 고려해서 하나하나 만들 수는 없으니까 퉁치기 위해 만드는 디폴트에러문
    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(Model model, Exception e) {

        System.out.println("Global 레벨의 기타 등등 공통 예외처리기 동작 시작!");
        model.addAttribute("e", e);

        return "error/default";
    }
}

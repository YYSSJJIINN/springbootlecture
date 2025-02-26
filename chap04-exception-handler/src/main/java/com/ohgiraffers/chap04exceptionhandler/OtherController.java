package com.ohgiraffers.chap04exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    /* 다른 컨트롤러에서 예외가 발생했을 때는
     * ExceptionHandlerController에 정의한 class-level의 @ExceptionHandler가 동작할 수 없다.
     * 대신 @ControllerAdvice가 정의된 컨트롤러의 @ExceptionHandler가 global-level로 동작해준다.
     * */
    @GetMapping("other-controller-null")
    public String otherNullPointerExceptionTest() {

        String str = null;
        System.out.println(str.charAt(0));

        return "/";
    }

    @GetMapping("other-controller-user")
    public String otherUserExceptionTest() throws MemberRegistException {

        boolean check = true;
        if(check) {
            throw new MemberRegistException("당신 같은 사람은 회원으로 받을 수 없습니다!");
        }

        return "/";
    }

    @GetMapping("other-controller-array")
    public String otherArrayExceptionTest() {

        double[] arr = new double[0];   // 길이 0 -> 엘리먼트 0개
        System.out.println(arr[0]);     // 첫 번째 엘리먼트 요청

        return "/";
    }
}

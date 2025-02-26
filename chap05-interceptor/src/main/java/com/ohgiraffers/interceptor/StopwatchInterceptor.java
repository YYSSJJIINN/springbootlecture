package com.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class StopwatchInterceptor implements HandlerInterceptor {

    private final MenuService menuService;

    public StopwatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    /* 전처리 메서드 */
    // Handler Interceptor에서 Handler로 출발하기 직전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("[StopwatchInterceptor] preHandle 호출됨...");

        long startTime = System.currentTimeMillis();        // 시작 시간 세틱
        request.setAttribute("startTime", startTime);

        /* true: 핸들러 호출 진행, false: 핸들러 호출 미진행 */
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /* 후처리 메서드 */
    // Handler에서 Handler Interceptor로 돌아온 직후
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        System.out.println("[StopwatchInterceptor] postHandle 호출됨...");

        // preHandle에서 request 객체에 담았던 값을 추출하여 시작 시간을 구함.
        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");       // 임시 값은 사용 후 삭제해주자.

        // 종료 시간
        long endTime = System.currentTimeMillis();

        // Model에 시작시간과 종료 시간의 차이를 계산하여 데이터를 담는다.
        modelAndView.addObject("interval", endTime - startTime);
    }

    /* 마지막에 호출되는 메서드 */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        System.out.println("[StopwatchInterceptor] afterCompletion 호출됨...");

        /* 인터셉터는 요청에 대한 응답이 반환된 이후에도 추가적인 로직을 수행할 수 있다. */
        menuService.testMethod();
    }
}

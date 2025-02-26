package com.ohgiraffers.chap04exceptionhandler;

/* 사용자 정의 예외 처리 클래스 */
public class MemberRegistException extends Exception {

    public MemberRegistException(String msg) {
        super(msg);
    }
}

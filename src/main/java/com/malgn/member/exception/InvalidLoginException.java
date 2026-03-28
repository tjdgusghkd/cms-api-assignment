package com.malgn.member.exception;

public class InvalidLoginException extends MemberException {

    public InvalidLoginException() {
        super("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
}

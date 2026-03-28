package com.malgn.member.exception;

public class PasswordMismatchException extends MemberException {

    public PasswordMismatchException() {
        super("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
}

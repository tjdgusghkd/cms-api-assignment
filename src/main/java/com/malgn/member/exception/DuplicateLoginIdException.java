package com.malgn.member.exception;

public class DuplicateLoginIdException extends MemberException {

    public DuplicateLoginIdException() {
        super("이미 사용 중인 아이디입니다.");
    }
}

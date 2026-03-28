package com.malgn.member.exception;

public abstract class MemberException extends RuntimeException {

    protected MemberException(String message) {
        super(message);
    }
}

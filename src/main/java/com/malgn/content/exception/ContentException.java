package com.malgn.content.exception;

public abstract class ContentException extends RuntimeException {

    protected ContentException(String message) {
        super(message);
    }
}
